//
//  RnEosEcc.swift
//  RNEosEcc
//
//  Created by Raphaël Gaudreault on 2019-01-20.
//  Copyright © 2019 Eva. All rights reserved.
//
import Foundation

@objc(Entrypoint)
class Entrypoint: NSObject {
    private var count = 0
    private var url = "http://localhost:8888"
    
    @objc
    func getInfo(_ callback: @escaping RCTResponseSenderBlock) {
        EOSRPC.endpoint = url
        EOSRPC.sharedInstance.chainInfo { (chainInfo, error) in
            if error == nil {
                let value = "\(chainInfo)"
                callback(["\(chainInfo!)"])
            } else {
                callback(["error"])
            }
        }
    }

    @objc func setUrl(_ scheme: NSString, urlSent: NSString, port: NSNumber) {
        let myScheme = scheme as String
        let myUrlSent = urlSent as String
        let myPort = port as! Int64
        url = myScheme + "://" + myUrlSent + ":" + String(myPort);
        EOSRPC.endpoint = url
    }

    @objc func pushAction(_ contract: NSString, action: NSString, message: NSString, permissionAccount: NSString, permissionType: NSString, privateKeyString: NSString, resolver resolve: @escaping RCTPromiseResolveBlock,  rejecter reject: @escaping RCTPromiseRejectBlock) {
        let myContract = contract as String
        let myAction = action as String
        let myMessage = message as String
        let myPermissionAccount = permissionAccount as String
        let myPermissionType = permissionType as String
        let myPrivateKeyString = privateKeyString as String
        let myPrivateKey = try! PrivateKey(keyString: myPrivateKeyString)
        EOSRPC.endpoint = url
        let abi = try! AbiJson(code: myContract, action: myAction, json: myMessage)
        
        TransactionUtil.pushTransaction(abi: abi, account: myPermissionAccount, privateKey: myPrivateKey!, completion: { (result, error) in
            if error != nil {
                if (error! as NSError).code == RPCErrorResponse.ErrorCode {
                    print("\(((error! as NSError).userInfo[RPCErrorResponse.ErrorKey] as! RPCErrorResponse).errorDescription())")
                    let returnValue = "\(((error! as NSError).userInfo[RPCErrorResponse.ErrorKey] as! RPCErrorResponse).errorDescription())"
                    //reject("ERROR", "ERROR", "ERROR"!)
                } else {
                    let returnValue = "other error: \(String(describing: error?.localizedDescription))"
                    print(returnValue)
                    //reject("ERROR", "ERROR", "ERROR"!)
                }
            } else {
                let returnValue = "Ok. Txid: \(result!.transactionId)"
                resolve(returnValue)
            }
        })
    }
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }
}




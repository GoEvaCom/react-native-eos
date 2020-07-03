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
    func getInfo(_ resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) {
        EOSRPC.endpoint = url
        EOSRPC.sharedInstance.chainInfo { (chainInfo, error) in
            if error == nil {
                let value = "\(chainInfo)"
                resolve("\(chainInfo!)")
            } else {
                reject("\(error)", "\(error)", error);
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
        let myPrivateKey: PrivateKey?
        let abi: AbiJson?

        //Validate and create Private Key 
        do {
            myPrivateKey = try PrivateKey(keyString: myPrivateKeyString)
        } catch let error {
            reject("private_key_error", "\(error)", error);
            return
        }

        //Validate and create ABI
        do {
            abi = try AbiJson(code: myContract, action: myAction, json: myMessage)
        } catch let error {
            reject("abi_json_error", "\(error)", error);
            return
        }
        
        EOSRPC.endpoint = url
        TransactionUtil.pushTransaction(abi: abi!, account: myPermissionAccount, privateKey: myPrivateKey!, completion: { (result, error) in
            if error != nil {
                reject("blockchain_error", "\(error)", error);
            } else {
                let returnValue = "{\"message\": \"Ok. Txid: \(result!.transactionId)\"}" //response.message (same as Android)
                resolve(returnValue)
            }
        })
    }
    
    public typealias ObjCCallback = (NSString) -> Void
    
    @objc func pushActionObjC(_ contract: NSString, action: NSString, message: NSString, permissionAccount: NSString, permissionType: NSString, privateKeyString: NSString, callback: @escaping ObjCCallback) {
        let myContract = contract as String
        let myAction = action as String
        let myMessage = message as String
        let myPermissionAccount = permissionAccount as String
        let myPermissionType = permissionType as String
        let myPrivateKeyString = privateKeyString as String
        let myPrivateKey: PrivateKey?
        let abi: AbiJson?

        //Validate and create Private Key 
        do {
            myPrivateKey = try PrivateKey(keyString: myPrivateKeyString)
        } catch let error {
            callback("private_key_error: \(error)" as NSString);
            return
        }

        //Validate and create ABI
        do {
            abi = try AbiJson(code: myContract, action: myAction, json: myMessage)
        } catch let error {
            callback("abi_json_error: \(error)" as NSString);
            return
        }
        
        EOSRPC.endpoint = url
        TransactionUtil.pushTransaction(abi: abi!, account: myPermissionAccount, privateKey: myPrivateKey!, completion: { (result, error) in
            if error != nil {
                let response = error?.localizedDescription ?? ""
                callback(response as NSString)
            } else {
                let response = "{\"message\": \"Ok. Txid: \(result!.transactionId)\"}" //response.message (same as Android)
                callback(response as NSString)
            }
        })
    }
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }
}




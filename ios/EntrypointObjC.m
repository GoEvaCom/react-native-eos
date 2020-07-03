//
//  EntrypointObjC.m
//  RNEosEcc
//
//  An instantializable objective-c wrapper for Entrypoint.swift
//
//  Needs two method calls to get ready:
//  setUrl(), setTransactionInfo()
//
//  Created by Jean-Marie Robatto on 2020-06-30.
//  Copyright © 2020 Eva Foundation. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RNEosEcc-Swift.h"
#if __has_include("RCTLog.h")
#import "RCTLog.h"
#else
#import <React/RCTLog.h>
#endif

@implementation EntrypointObjC

Entrypoint *entrypoint;
NSString *_contract_account;
NSString *_action;
NSString *_message;
NSString *_permissionAccount;
NSString *_permissionType;
NSString *_privateKeyString;

-(id)init {
   self = [super init];
   entrypoint = [[Entrypoint alloc] init];
   return self;
}

- (void)setUrl:(NSString*)scheme url:(NSString*)url port:(NSNumber*)port
{
    [entrypoint setUrl:scheme urlSent:url port:port];
}

-(void)setTransactionInfo:(NSString*)contract_account action:(NSString*)action  permissionAccount:(NSString*)permissionAccount permissionType:(NSString*)permissionType privateKeyString:(NSString*)privateKeyString
{
    _contract_account = contract_account;
    _action = action;
    _permissionAccount = permissionAccount;
    _permissionType = permissionType;
    _contract_account = contract_account;
    _privateKeyString = privateKeyString;
}

-(void)setMessage:(NSString*)message
{
    _message = message;
}

-(void)pushAction
{
    [entrypoint pushActionObjC:_contract_account action:_action message:_message permissionAccount:_permissionAccount permissionType:_permissionType privateKeyString:_privateKeyString callback:^(NSString* response) { RCTLogInfo(@"%@", [NSString stringWithFormat:@"RCTBackgroundGeolocation Chain Response: %@", response]); }];
}
 
@end

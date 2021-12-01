//
//  EntrypointObjC.h
//  RNEosEcc
//
//  Created by Jean-Marie Robatto on 2020-06-30.
//  Copyright © 2020 Eva Foundation. All rights reserved.
//

#ifndef EntrypointObjC_h
#define EntrypointObjC_h

@interface EntrypointObjC : NSObject;

-(void)setUrl:(NSString*)scheme url:(NSString*)url port:(NSNumber*)port;
-(void)setTransactionInfo:(NSString*)contract_account action:(NSString*)action permissionAccount:(NSString*)permissionAccount permissionType:(NSString*)permissionType privateKeyString:(NSString*)privateKeyString;
-(void)setMessage:(NSString*)message;
-(void)pushAction:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock)failure;

@end


#endif /* EntrypointObjC_h */

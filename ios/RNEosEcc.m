
#import "RNEosEcc.h"

@interface RCT_EXTERN_REMAP_MODULE(RNEosEcc, Entrypoint, NSObject)
RCT_EXTERN_METHOD(getInfo: (RCTResponseSenderBlock)callback)
RCT_EXTERN_METHOD(
                  setUrl:(nonnull NSString *)scheme
                  urlSent:(nonnull NSString *)urlSent
                  port:(nonnull NSNumber *)port
                  )
RCT_EXTERN_METHOD(
                  pushAction:(nonnull NSString *)contract
                  action:(nonnull NSString *)action
                  message:(nonnull NSString *)message
                  permissionAccount:(nonnull NSString *)permissionAccount
                  permissionType:(nonnull NSString *)permissionType
                  privateKeyString:(nonnull NSString *)privateKeyString
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )
@end

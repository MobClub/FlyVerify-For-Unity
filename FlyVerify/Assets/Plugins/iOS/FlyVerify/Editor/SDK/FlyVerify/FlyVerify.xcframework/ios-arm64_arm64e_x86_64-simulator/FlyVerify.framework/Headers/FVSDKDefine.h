//
//  FVSDKDefine.h
//  FVSDKDefine
//
//  Created by fly on 2019/5/17.
//  Copyright © 2019 fly. All rights reserved.
//

#ifndef FVSDKDefine_h
#define FVSDKDefine_h

#import <Foundation/Foundation.h>

//SDK版本号
#define KFVSDKVersion @"13.6.2"
//产品标识
#define KFVIdentifier @"FLYVERIFY"

/**
 FlyVerify 结果的回调

 @param resultDic 结果的字典
 @param error 错误信息
 */
typedef void(^FlyVerifyResultHander)(NSDictionary * _Nullable resultDic, NSError * _Nullable error);

/**
 获取的运营商类型
 **/
typedef NS_ENUM(NSUInteger, FlyVerifyOperatorType) {
    FlyVerifyOperatorMask = 0xFF,
    FlyVerifyOperatorMobile = 1 << 1, //移动
    FlyVerifyOperatorUnion = 1 << 2,  //联通
    FlyVerifyOperatorTelecom = 1 << 3, //电信
    FlyVerifyOperatorUnkown = 1 << 4,  //未知
    FlyVerifyOperatorNone = 1 << 5 //无运营商
};


#endif /* FVSDKDefine_h */

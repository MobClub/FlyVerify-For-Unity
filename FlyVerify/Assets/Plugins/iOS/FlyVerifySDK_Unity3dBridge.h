//
//  FlyVerifySDK_Unity3dBridge.h
//  FlyVerify_Unity3d
//
//  Created by Jacky on 21/2/20.
//  Copyright © 2016年 liys. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FlyVerifySDK_Unity3dBridge : NSObject

@end

@interface UIColor (Hex)

// 透明度固定为1，以0x开头的十六进制转换成的颜色
+ (UIColor *)colorWithHex:(long)hexColor;

// 0x开头的十六进制转换成的颜色,透明度可调整
+ (UIColor *)colorWithHex:(long)hexColor alpha:(float)opacity;

/// iOS中十六进制的颜色转换为UIColor
/// @param colorString 十六进制的颜色字符串（支持以#或0x开头）
+ (UIColor *)colorWithHexString:(NSString *)colorString;

@end

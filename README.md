# 模拟位置 (FakeLocation)

- English description please refer to [here](https://github.com/j2rong/FakeLocation#english).
- 这是一个 ***Xposed*** 模块，用于模拟地理位置，世界在手天下我有。
- 无广告、无需模拟位置权限

<br/>

## 屏幕截图 (Screenshots)

<p align="center">
<img src="https://github.com/j2rong/FakeLocation/blob/master/art/screenshots/main_0_1_44.png" width="216"></a>
<img src="https://github.com/j2rong/FakeLocation/blob/master/art/screenshots/settings_0_1_44.png" width="216"></a>
<img src="https://github.com/j2rong/FakeLocation/blob/master/art/screenshots/per_app_settings_0_3_73.png" width="216"></a>
</p>
<br/>

## 更新日志 (Changelogs)

- **0.6.195**
  - 支持Android 4.3    
  - 基站模拟增强    
  - 完善部分函数的处理逻辑    
  - 新增对Google Play services Location API的支持    
  - 新增对腾讯定位SDK的支持    

- **0.5.161**
  - 新增模板    
  - 坐标偏移修正    
  - 基站模拟增强    
  - 修复一个可能导致崩溃的错误    
  - 修复部分设置不能生效的错误    
  - 新增的WRITE_EXTERNAL_STORAGE权限用于记录崩溃日志    
  
- **0.4.127**
  - 新增应用搜索    
  - 新增快速设置（最近列表）    
  - 修复一些错误    
  - 一些性能优化    

- **0.3.78**
  - 新增基站模拟
  - 新增繁体中文 (thanks to iamernie8199)

- **0.2.48**
  - 添加对4.4系统的支持

- **0.1.44**
  - 初始版本

<br/>

## 问题

- 如使用过程中出现问题或功能建议，请至[此处](https://github.com/j2rong/FakeLocation/issues/new)提交。
- 如位置设置无效，请在模块中打开日志，并将Xposed Installer中的日志提交至[此处](https://github.com/j2rong/FakeLocation/issues/new)。

<br/>

## 致谢 (Credits)

- [**Xposed Framework**](https://github.com/rovo89/Xposed)

  Original work Copyright (c) 2005-2008, The Android Open Source Project    
  Modified work Copyright (c) 2013, rovo89 and Tungstwenty    

  Licensed under the Apache License, Version 2.0 (the "License");    
  you may not use this file except in compliance with the License.    

  Unless required by applicable law or agreed to in writing, software   
  distributed under the License is distributed on an "AS IS" BASIS,   
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   
  See the License for the specific language governing permissions and   
  limitations under the License.

<br/>

## English

- This is an ***Xposed*** module for mocking locations per app

- No Ads, No need to turn on "Mock locations"

- **Problems**

  Feel free to [open an issue](https://github.com/j2rong/FakeLocation/issues/new) if there is any problem or suggestion.      
  If fake location don't work, you can turn on the logs and submit (you can find it in Xposed Installer) to help identify the issue.


- **Changelogs**

  - **0.6.195**     
    Support Android 4.3     
    Enhance cell location mocking     
    Improve the handling logic of several functions     
    Add support for the Location API of Google Play services     
    Add support for the Tencent Location SDK     

  - **0.5.161**    
    Add template settings     
	Offset correction (coordinates in China)     
	Enhance cell location mocking    
	Fix a bug that may cause FC    
	Fix a bug that new settings won't take effect    
	New permission WRITE_EXTERNAL_STORAGE is used for saving crash logs    

  - **0.4.127**    
    Add app search    
    Add setting shortcut (recent list)    
    Bug fixes    
    Performance optimizations    

  - **0.3.78**    
    Add cell location mocking     
    Add Tradictional Chinese translation (thanks to iamernie8199)    

  - **0.2.48**    
    Add support for Android 4.4    

  - **0.1.44**    
    First release    
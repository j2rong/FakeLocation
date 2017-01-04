# 更新日志 (Changelogs)

## 1.2
- 准备对Android 7的支持（部分）
- 应用设置页恢复“更新”按钮
- 尝试修复部分GPS定位问题
- 其他一些改进      
<br/>
- Prepare Android 7 support (partial)
- Add "Notify" button back in `Per-app Settings` page
- Try to fix some issues regarding to GPS mocking
- Other improvements


## 1.1.646
- 地图添加搜索支持
- 完善当前基站信息获取
- 帮助中的页面链接现在使用浏览器打开
- 打开地图默认定位到应用之前设置的位置
- UI细节调整
- 修复错误      
<br/>
- Add map search
- Improve 'Current Cell Info'
- Links in help now use browser to open
- Map's initial location default to app's current settings
- UI adjustments
- Bug fixes


## 1.0.561
- 完善对Gps状态的处理
- 悬浮窗及应用设置页新增“更新”按钮，单独使用GPS时应用出现“正在定位”、“无法定位”等可尝试点击
- 改进内存不足时悬浮窗服务可能被系统关闭的问题      
<br/>
- Improve Gps status handling
- Add a "Notify" button (overlay and per-app settings), try it when you use GPS Mocking alone and get "Locating", "Unable to locate", etc.
- Improve that overlay may be killed by the system when low on memory


## 1.0.527
- 新增`模板`-`当前基站信息`
- 修复错误
- 易用性改进     
<br/>
- Add `Template` - `Current Cell Info`
- Bug fixes
- Improve usability


## 0.9.486
- 新增自我隐藏
- 应用列表根据应用是否启用模拟排序
- GMS支持使用新的实现（现在无需单独设置com.google.android.gms，直接设置对应APP即可）
- 修复可能跳转回真实地址的错误
- 兼容跳转至Material Xposed Installer模块设置页面
- 更新帮助内容
- 更新间隔设置
- 修复错误     
<br/>
- Hide module from selected app(s)
- Sort apps list by mocking status and settings
- New approach for GMS support (Make the gps settings in App's per-app settings page, no longer need to set com.google.android.gms separately)
- Fix location jumping back to real location
- Fix a problem with Material Xposed Installer
- Update help
- Settings for update interval
- Bug fixes


## 0.8.400
- 现在无需打开GPS即可实时模拟新的位置
- 优化捐赠方式
- 如微信无法打开，在设置中打开兼容模式
- 新增摇杆悬浮窗，根据方向、力量值更新位置信息，使用时先在对应App设置页面点击“关联悬浮窗”按钮。
- 添加首选地图设置
- 修复部分手机上启用模块后仍然显示模块未启用的错误
- 修复列表为空时点击搜索闪退的错误
- 修复一些错误
- 请注意：新版使用了新的设置文件和格式，更新后部分设置需重新设置    
<br/>
- Now you don't even need to turn on GPS to update new mocking location
- Optimize donation 
- Add joystick overlay, mocking location will be updated according to the bearing and strength. To use it, go to the app setting page and click "Connect" first.
- Add preferred map setting
- Fix a bug that the module keeps saying that it's not active even it was enabled in Xposed Installer on some devices
- Fix a crash cause by clicking search when the list is still loading
- Other bug fixes
- PLEASE NOTE that this update uses a new setting file and new format, some old settings will be lost after the update. 


## 0.7.306
- 修复点击应用列表闪退的错误
- 修复加载应用列表可能导致的OOM错误     
<br/>
- Fix a crash when selecting app from the list     
- Fix an OOM crash when loading a long list of apps


## 0.7.300
- 自定义GPS状态     
- 新增地图选择     
- 地图选择历史（最近列表）     
- 修复一些错误     
- 开启“即时更新”后，地图点击、选择（点击）标记、使用最近列表中的地点将直接写入设置中，无需返回应用列表。    
<br/>
- Custom GPS status support     
- Select GPS coordinates from map (play service 7.0.0+ is required)     
- Map select history (recent list)     
- Bug fixes     
- When "Instant Update" enabled, location settings made by map click, marker click, recent list selection will be saved immediately without going back to app list. 


## 0.7.291
- 测试版本 (Internal release)


## 0.6.195
- 支持Android 4.3    
- 基站模拟增强    
- 完善部分函数的处理逻辑    
- 新增对Google Play services Location API的支持    
- 新增对腾讯定位SDK的支持     
<br/>
- Support Android 4.3     
- Enhance cell location mocking     
- Improve the handling logic of several functions     
- Add support for the Location API of Google Play services     
- Add support for the Tencent Location SDK 


## 0.5.161
- 新增模板    
- 坐标偏移修正    
- 基站模拟增强    
- 修复一个可能导致崩溃的错误    
- 修复部分设置不能生效的错误    
- 新增的WRITE_EXTERNAL_STORAGE权限用于记录崩溃日志     
<br/>
- Add template settings     
- Offset correction (coordinates in China)     
- Enhance cell location mocking    
- Fix a bug that may cause FC    
- Fix a bug that new settings won't take effect    
- New permission WRITE_EXTERNAL_STORAGE is used for saving crash logs

  
## 0.4.127
- 新增应用搜索    
- 新增快速设置（最近列表）    
- 修复一些错误    
- 一些性能优化    
<br/>
- Add app search    
- Add setting shortcut (recent list)    
- Bug fixes    
- Performance optimizations


## 0.3.78
- 新增基站模拟
- 新增繁体中文 (thanks to iamernie8199)    
<br/>
- Add cell location mocking     
- Add Tradictional Chinese translation (thanks to iamernie8199)


## 0.2.48
- 添加对4.4系统的支持    
<br/>
- Add support for Android 4.4


## 0.1.44
- 初始版本    
<br/>
- First release

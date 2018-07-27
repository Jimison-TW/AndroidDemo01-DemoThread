# AndroidDemo01-DemoThread
##開發版本
Andorid 3.1.2
SdkVersion 27
minSdkVersion 15
targetSdkVersion 27

##runOnUiThread()方法的運用
這個Demo可以分為兩個部分，一個是上方的兩個button，點下後都會修改button上的文字，但一個是在主執行緒修改，另一個在背景

在較舊的版本中，背景執行緒是無法變更UI畫面的內容，須透過runOnUiThread()方法，否則程式會報錯死亡。

但在新版本中似乎對這項規定，又做了修正允許通融了，這部分要再看官方文件詳解

##執行緒跑動的過程
點擊switch按鈕後，progress bar會開始跑動，可以看到在不同時間間隔下執行緒的運作情況

##學習重點
了解Thread(Java原生)與Handler(android中新增的)這兩種執行緒在Android Studio中的使用方法

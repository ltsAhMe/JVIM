# SwingVIM

java swing写的一个简易文本编辑器 没什么好说的
### 使用自己的字体
替换jar中的font.ttf为你的 例如无法显示中文 请自己替换中文支持字体(默认为JetBrains Mono)
### 命令
详见jar中Commends(JVIM/Commend/Commends)
### 自定义代码高亮
格式参考java_CHL(JVIM/code/java_CHL)\
可在HighLight.java中添加\
参考   
```java
            case "java":
                thepath = "JVIM/code/java_CHL";
                break;
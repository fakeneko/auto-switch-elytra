# 使用MultiLoader-Template

## 下载文件

下载对应版本的文件
<https://github.com/jaredlll08/MultiLoader-Template/>

## 修改settings.gradle

修改rootProject.name的内容

```gradle
rootProject.name = 'auto_switch_elytra'
```

## 修改gradle-wrapper.properties

把distributionUrl替换成本地

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=file:///D:/workspace/gradle/gradle-9.2.0-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

国内阿里数据源
<https://mirrors.aliyun.com/github/releases/gradle/gradle-distributions/?spm=a2c6h.25603864.0.0.2c7cf540jjEsZX>

## 修改gradle.properties

修改基本配置

```properties
version=26.1
group=cn.com.fakeneko

mod_name=Auto Switch Elytra
mod_author=fakeneko
mod_id=auto_switch_elytra
```

如果是版本不对，还需要修改版本配置

```properties
# 看mc的版本
java_version=25
minecraft_version=26.1

fabric_version=0.144.0+26.1
fabric_loader_version=0.18.4

neoforge_version=26.1.0.1-beta
neoforge_loader_version_range=[4,)
```


## 使用idea打开项目

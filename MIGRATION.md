# Auto Switch Elytra — Multiloader 迁移记录

将独立的 `auto_switch_elytra-fabric` 与 `auto_switch_elytra-neoforge` 项目合并到 `auto_switch_elytra` multiloader 模板工程中。

---

## 一、项目结构调整

删除原模板中的 `com.example.examplemod` 示例代码，统一使用 `cn.com.fakeneko` 包名。

### 1.1 Common 模块（新增/替换）

| 文件路径 | 说明 |
|---|---|
| `common/src/main/java/cn/com/fakeneko/Constants.java` | 模组常量：`MOD_ID`、`MOD_NAME`、`LOG` |
| `common/src/main/java/cn/com/fakeneko/CommonClass.java` | 通用初始化逻辑（加载配置、模组存在检测） |
| `common/src/main/java/cn/com/fakeneko/platform/Services.java` | ServiceLoader 工具类 |
| `common/src/main/java/cn/com/fakeneko/platform/services/IPlatformHelper.java` | 平台抽象接口 |
| `common/src/main/java/cn/com/fakeneko/reflection/IGetFilePathHelper.java` | 配置文件路径获取接口 |
| `common/src/main/java/cn/com/fakeneko/commonConfig/ConfigOption.java` | 配置项泛型包装 |
| `common/src/main/java/cn/com/fakeneko/commonConfig/ModConfig.java` | JSON 配置读写（Gson） |
| `common/src/main/java/cn/com/fakeneko/commonConfig/ConfigServices.java` | ServiceLoader 加载 `IGetFilePathHelper` |
| `common/src/main/java/cn/com/fakeneko/commonConfig/ScreenBuilder.java` | Cloth Config 配置界面构建器 |
| `common/src/main/java/cn/com/fakeneko/commonConfig/ScreenBuilderYacl.java` | YACL 配置界面构建器 |
| `common/src/main/java/cn/com/fakeneko/mixin/MixinClientPlayerEntity.java` | 自动切换鞘翅 Mixin（客户端） |
| `common/src/main/java/cn/com/fakeneko/mixin/MixinArmorStandEntity.java` | 禁用盔甲架交互 Mixin（客户端） |
| `common/src/main/resources/auto_switch_elytra.mixins.json` | Common Mixin 配置 |
| `common/src/main/resources/pack.mcmeta` | 资源包描述 |
| `common/src/main/resources/assets/auto_switch_elytra/icon.png` | 模组图标 |
| `common/src/main/resources/assets/auto_switch_elytra/lang/en_us.json` | 英文本地化 |
| `common/src/main/resources/assets/auto_switch_elytra/lang/zh_cn.json` | 中文本地化 |

> **注意**：`pack.mcmeta` 中的 `pack_format` 保持模板原值 `8`，未做修改。

### 1.2 Fabric 模块（新增/替换）

| 文件路径 | 说明 |
|---|---|
| `fabric/src/main/java/cn/com/fakeneko/AutoSwitchElytra.java` | Fabric 入口（`ModInitializer`） |
| `fabric/src/main/java/cn/com/fakeneko/platform/FabricPlatformHelper.java` | `IPlatformHelper` Fabric 实现 |
| `fabric/src/main/java/cn/com/fakeneko/config/FabricGetFilePathHelper.java` | `IGetFilePathHelper` Fabric 实现 |
| `fabric/src/main/java/cn/com/fakeneko/client/Keybinds/FabricKeyBindings.java` | 快捷键注册（`ClientModInitializer`） |
| `fabric/src/main/java/cn/com/fakeneko/client/modmenu/FabricModmeunApi.java` | ModMenu 配置页集成 |
| `fabric/src/main/resources/fabric.mod.json` | Fabric 模组描述 |
| `fabric/src/main/resources/auto_switch_elytra.fabric.mixins.json` | Fabric 专属 Mixin 配置（当前为空） |
| `fabric/src/main/resources/META-INF/services/cn.com.fakeneko.platform.services.IPlatformHelper` | ServiceLoader 指向 `FabricPlatformHelper` |
| `fabric/src/main/resources/META-INF/services/cn.com.fakeneko.reflection.IGetFilePathHelper` | ServiceLoader 指向 `FabricGetFilePathHelper` |

### 1.3 NeoForge 模块（新增/替换）

| 文件路径 | 说明 |
|---|---|
| `neoforge/src/main/java/cn/com/fakeneko/AutoSwitchElytra.java` | NeoForge 入口（`@Mod`） |
| `neoforge/src/main/java/cn/com/fakeneko/platform/NeoForgePlatformHelper.java` | `IPlatformHelper` NeoForge 实现 |
| `neoforge/src/main/java/cn/com/fakeneko/config/NeoForgeGetFilePathHelper.java` | `IGetFilePathHelper` NeoForge 实现 |
| `neoforge/src/main/java/cn/com/fakeneko/Keybinds/NeoForgeKeyBindings.java` | 快捷键注册（`@EventBusSubscriber`） |
| `neoforge/src/main/java/cn/com/fakeneko/modmenu/NeoForgeModListApi.java` | 模组列表配置页注册（`IConfigScreenFactory`） |
| `neoforge/src/main/resources/META-INF/neoforge.mods.toml` | NeoForge 模组描述 |
| `neoforge/src/main/resources/auto_switch_elytra.neoforge.mixins.json` | NeoForge 专属 Mixin 配置（当前为空） |
| `neoforge/src/main/resources/META-INF/services/cn.com.fakeneko.platform.services.IPlatformHelper` | ServiceLoader 指向 `NeoForgePlatformHelper` |
| `neoforge/src/main/resources/META-INF/services/cn.com.fakeneko.reflection.IGetFilePathHelper` | ServiceLoader 指向 `NeoForgeGetFilePathHelper` |

### 1.4 删除的文件

- 所有 `com/example/examplemod` 包下的 Java 源文件（Common、Fabric、NeoForge 三个模块）
- `examplemod.mixins.json`、`examplemod.fabric.mixins.json`、`examplemod.neoforge.mixins.json`
- 旧版 `META-INF/services/com.example.examplemod.platform.services.IPlatformHelper`

---

## 二、Gradle 配置调整

### 2.1 `gradle.properties`（新增属性）

```properties
cloth_config_version=26.1.154
modmenu_version=18.0.0-alpha.8
yacl_version_fabric=3.9.3+26.1-fabric
yacl_version_neoforge=3.9.3+26.1-neoforge
```

### 2.2 `buildSrc/src/main/groovy/multiloader-common.gradle`

- **新增仓库**：`maven.shedaniel.me`、`maven.isxander.dev/releases`、`maven.fabricmc.net`
- **JAR 命名**：为所有 `Jar` 类型任务设置 `archiveVersion = ''`，使输出文件名格式为 `auto_switch_elytra-{loader}-{minecraft_version}.jar`，不再追加程序版本号

### 2.3 `common/build.gradle`

- 以 `compileOnly` + `transitive = false` 引入 `cloth-config-fabric` 和 `yet-another-config-lib`，供 Common 模块编译配置界面类，同时避免将 Fabric API 等传递依赖拉入 Common

### 2.4 `fabric/build.gradle`

- 新增 repositories：`maven.shedaniel.me`、`maven.terraformersmc.com`、`maven.isxander.dev/releases`
- 新增 dependencies：
  - `me.shedaniel.cloth:cloth-config-fabric`
  - `com.terraformersmc:modmenu`
  - `dev.isxander:yet-another-config-lib`

### 2.5 `neoforge/build.gradle`

- 新增 repositories：`maven.shedaniel.me`、`maven.isxander.dev/releases`
- 新增 dependencies：
  - `me.shedaniel.cloth:cloth-config-neoforge`
  - `dev.isxander:yet-another-config-lib`（`compileOnly`，与参考项目保持一致）

---

## 三、GitHub Actions

新增 `.github/workflows/build.yml`：

- 触发条件：`push` 任意 tag
- JDK 25（Microsoft distribution）
- 构建命令：`./gradlew build`
- Release 产物：仅上传 `fabric` 和 `neoforge` 的主 JAR（过滤掉 `-sources.jar`、`-javadoc.jar` 及 `common` 中间产物）

---

## 四、构建结果

执行 `./gradlew clean build` 后，各模块输出如下：

```
common/build/libs/
  auto_switch_elytra-common-26.1.jar
  auto_switch_elytra-common-26.1-sources.jar
  auto_switch_elytra-common-26.1-javadoc.jar

fabric/build/libs/
  auto_switch_elytra-fabric-26.1.jar          ← 实际发布的 Fabric 模组
  auto_switch_elytra-fabric-26.1-sources.jar
  auto_switch_elytra-fabric-26.1-javadoc.jar

neoforge/build/libs/
  auto_switch_elytra-neoforge-26.1.jar        ← 实际发布的 NeoForge 模组
  auto_switch_elytra-neoforge-26.1-sources.jar
  auto_switch_elytra-neoforge-26.1-javadoc.jar
```

---

## 五、注意事项

1. **Mixin 位置**：`MixinClientPlayerEntity` 与 `MixinArmorStandEntity` 放在 Common 模块，通过 `auto_switch_elytra.mixins.json` 的 `"client"` 数组声明，Fabric 与 NeoForge 共享同一份 Mixin 代码。
2. **配置界面**：`ScreenBuilder`（Cloth Config）和 `ScreenBuilderYacl`（YACL）放在 Common 模块，由两个 Loader 模块按需调用。运行时不依赖的库通过条件判断（`CommonClass.isClothConfigLoaded()` / `isYaclLoaded()`）做保护。
3. **ServiceLoader**：`IPlatformHelper` 和 `IGetFilePathHelper` 均通过 `META-INF/services` 机制在各自 Loader 模块中提供实现，Common 模块通过 `ServiceLoader` 统一加载。
4. **JAR 命名**：通过 `tasks.withType(Jar).configureEach { archiveVersion = '' }` 统一去掉了 Gradle 默认附加的 `version` 后缀。
5. **本地 Gradle**：本项目强制使用本地 Gradle，不使用 Wrapper 在线下载。后续若迁移到新环境或升级 Gradle 版本，需手工修改 `gradle/wrapper/gradle-wrapper.properties` 中的 `distributionUrl` 路径。

---

## 六、本地 Gradle 配置（必读）

本项目**不使用** Gradle Wrapper 在线下载，强制指向本地 Gradle。若更换开发环境或升级 Gradle，需手工修改以下配置。

文件：`gradle/wrapper/gradle-wrapper.properties`

```properties
distributionUrl=file:///D:/workspace/gradle/gradle-9.2.0-bin.zip
```

- 将 `distributionUrl` 改为本地 Gradle 压缩包的绝对路径（`file:///` 协议）。
- 示例中的路径为 `D:/workspace/gradle/gradle-9.2.0-bin.zip`，请按实际安装位置调整。

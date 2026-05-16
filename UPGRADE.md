# Auto Switch Elytra — 版本升级指南

当 Minecraft 发布新版本（如 26.1 → 26.2）时，按以下清单逐项更新即可。

---

## 一、升级清单

### 1.1 `gradle.properties`（核心版本号）

| 属性 | 当前值（26.1） | 升级动作 |
|---|---|---|
| `version` | `26.1` | 改为新版本号 |
| `minecraft_version` | `26.1` | 改为 `26.2` |
| `minecraft_version_range` | `[26.1, 26.2)` | 改为 `[26.2, 26.3)` |
| `neo_form_version` | `26.1-1` | 查询 NeoForm 对应版本 |
| `fabric_version` | `0.144.0+26.1` | 查询 Fabric API 对应版本 |
| `fabric_loader_version` | `0.18.4` | 查询 Fabric Loader 对应版本 |
| `neoforge_version` | `26.1.0.1-beta` | 查询 NeoForge 对应版本 |
| `java_version` | `25` | 若 MC 26.2 要求更高 JDK 则更新 |

### 1.2 可选依赖版本（需去对应仓库查询新版）

| 属性 | 查询地址 |
|---|---|
| `cloth_config_version` | https://maven.shedaniel.me/ |
| `modmenu_version` | https://maven.terraformersmc.com/releases/ |
| `yacl_version_fabric` | https://maven.isxander.dev/releases |
| `yacl_version_neoforge` | https://maven.isxander.dev/releases |

> 建议同时更新 Fabric Loader 和 NeoForge 到各自官方推荐的最低版本。

### 1.3 资源文件

| 文件 | 检查项 |
|---|---|
| `common/src/main/resources/pack.mcmeta` | `pack_format` 可能需要更新（查询 Minecraft Wiki 对应版本的 `pack_format`） |
| `common/src/main/resources/auto_switch_elytra.mixins.json` | `compatibilityLevel` 若 JDK 升级需同步调整 |
| `fabric/src/main/resources/auto_switch_elytra.fabric.mixins.json` | 同上 |
| `neoforge/src/main/resources/auto_switch_elytra.neoforge.mixins.json` | 同上 |

### 1.4 代码兼容性检查

升级后执行 `./gradlew build`，重点检查以下 Mixin 目标是否因 MC 版本变更而失效：

- `MixinClientPlayerEntity`
  - `LocalPlayer#aiStep` 方法签名
  - `LocalPlayer#tryToStartFallFlying` 方法签名
  - `LocalPlayer#isFallFlying` 方法签名
  - `LocalPlayer#getItemBySlot(EquipmentSlot)`
  - `InventoryMenu#slots` 及 `Slot#getContainerSlot`
  - `MultiPlayerGameMode#handleContainerInput` 参数列表
- `MixinArmorStandEntity`
  - `ArmorStand#interact` 方法签名

若 Minecraft 内部类或方法映射发生变更，需同步修正对应的 Mixin `target` 和注入点。

### 1.5 发布前验证清单

- [ ] `./gradlew clean build` 三模块全部通过
- [ ] Fabric Client / Server 可正常启动
- [ ] NeoForge Client / Server 可正常启动
- [ ] 配置界面（Cloth Config / YACL）可正常打开并保存
- [ ] 快捷键可打开配置界面
- [ ] 自动切换鞘翅功能正常
- [ ] 禁用盔甲架交互功能正常
- [ ] GitHub Actions 构建通过并正确发布 Release

---

## 二、注意事项

1. **Mixin 位置**：`MixinClientPlayerEntity` 与 `MixinArmorStandEntity` 放在 Common 模块，通过 `auto_switch_elytra.mixins.json` 的 `"client"` 数组声明，Fabric 与 NeoForge 共享同一份 Mixin 代码。
2. **配置界面**：`ScreenBuilder`（Cloth Config）和 `ScreenBuilderYacl`（YACL）放在 Common 模块，由两个 Loader 模块按需调用。运行时不依赖的库通过条件判断（`CommonClass.isClothConfigLoaded()` / `isYaclLoaded()`）做保护。
3. **ServiceLoader**：`IPlatformHelper` 和 `IGetFilePathHelper` 均通过 `META-INF/services` 机制在各自 Loader 模块中提供实现，Common 模块通过 `ServiceLoader` 统一加载。
4. **JAR 命名**：通过 `tasks.withType(Jar).configureEach { archiveVersion = '' }` 统一去掉了 Gradle 默认附加的 `version` 后缀。
5. **本地 Gradle**：本项目强制使用本地 Gradle，不使用 Wrapper 在线下载。后续若迁移到新环境或升级 Gradle 版本，需手工修改 `gradle/wrapper/gradle-wrapper.properties` 中的 `distributionUrl` 路径。

---

## 三、本地 Gradle 配置（必读）

本项目**不使用** Gradle Wrapper 在线下载，强制指向本地 Gradle。若更换开发环境或升级 Gradle，需手工修改以下配置。

文件：`gradle/wrapper/gradle-wrapper.properties`

```properties
distributionUrl=file:///D:/workspace/gradle/gradle-9.2.0-bin.zip
```

- 将 `distributionUrl` 改为本地 Gradle 压缩包的绝对路径（`file:///` 协议）。
- 示例中的路径为 `D:/workspace/gradle/gradle-9.2.0-bin.zip`，请按实际安装位置调整。

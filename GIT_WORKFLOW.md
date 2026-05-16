# Git 操作记录

本项目的 Git 仓库初始化、提交、推送及发布流程。

---

## 一、仓库初始化

```bash
# 初始化本地仓库
git init

# 添加远程仓库
git remote add origin https://github.com/fakeneko/auto-switch-elytra.git
```

---

## 二、日常提交流程

```bash
# 查看修改状态
git status

# 添加所有变更
git add -A

# 提交（提交信息格式：类型 + 简短描述）
git commit -m "feat: 添加新功能"

# 推送到远程 v26.1 分支
git push origin v26.1
```

---

## 三、Release 发布流程

### 3.1 创建并推送 Tag

本项目使用 Tag 触发 GitHub Actions 自动构建和发布。

```bash
# 创建附注 Tag
git tag -a 26.1 -m "Release 26.1"

# 推送 Tag 到远程
git push origin 26.1
```

推送 Tag 后，GitHub Actions 会自动执行 `./.github/workflows/build.yml`：
1. 使用 `setup-gradle` action 安装 Gradle（因为 Wrapper 配置为本地路径，CI 无法使用）
2. 执行 `gradle build` 构建三模块
3. 自动创建 Release 并上传 `fabric` 和 `neoforge` 的构建产物

### 3.2 重新发布（Tag 已存在时）

如果 Tag 已存在且需要重新发布（如修复 CI 配置后）：

```bash
# 删除本地 Tag
git tag -d 26.1

# 删除远程 Tag
git push origin --delete 26.1

# 重新创建并推送
git tag -a 26.1 -m "Release 26.1"
git push origin 26.1
```

---

## 四、分支说明

| 分支 | 用途 |
|---|---|
| `v26.1` | 当前开发分支，对应 Minecraft 26.1 版本 |

> 注意：本项目不使用 `master`/`main` 作为默认分支，版本分支命名格式为 `v{mc_version}`。

---

## 五、常见问题

### 5.1 CI 报错 `FileNotFoundException: /D:/workspace/gradle/gradle-9.2.0-bin.zip`

原因：GitHub Actions 的 Linux Runner 无法访问 Windows 本地路径。

解决：`.github/workflows/build.yml` 使用 `gradle/actions/setup-gradle` action 安装 Gradle，构建命令使用 `gradle build` 而非 `./gradlew build`。

本地开发仍使用 Wrapper 的 `file:///` 路径指向本地 Gradle。

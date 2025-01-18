platforms=("fabric" "neoforge")
mod_name="auto_switch_elytra"
mod_version="1.20.6"
for platform in "${platforms[@]}"
do
  filename="${mod_name}-${platform}-${mod_version}.jar"
  if test -f "./CompiledJar/${filename}"; then
    rm  "./CompiledJar/${filename}"
  fi
  cp ./${platform}/build/libs/${filename} ./CompiledJar/
done


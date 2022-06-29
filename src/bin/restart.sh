#! /bin/shell

# 项目名称
APPLICATION="Young-Board"

# 停服
echo stop ${APPLICATION} Application...
sh shutdown.sh

# 启动服务
echo start ${APPLICATION} Application...
sh startup.sh
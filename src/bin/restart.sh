#! /bin/bash

# 项目名称
APPLICATION="Young-Board"

BIN_PATH=$(cd `dirname $0`; pwd)
# 停服
echo stop ${APPLICATION} Application...
sh ${BIN_PATH}/shutdown.sh

# 启动服务
echo start ${APPLICATION} Application...
sh ${BIN_PATH}/startup.sh
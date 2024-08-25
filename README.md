## Dependency
- TorosamyCore
## Function
- Quickly initiate requests through stealth and right-click
- Quickly process requests by clicking on the chat bar
- Block other people's transaction requests separately to prevent harassment
- Monitor the content of transactions through logs
## FuturePlans
- Add economic system
## Usage
1. download [TorosamyCore](https://github.com/ToroSamy/TorosamyCore)  as a dependency plugin
2. put the **dependencies** and this plugin into your plugins folder and start your server
3. Modify the default configuration file generated and restart your server
4. Determine whether to hold down the stealth key based on your configuration file and right-click
## Permission
- - **Usage:** /trade reload
  - **Description:** reload this plugin
  - **Permission:** safetrade.reload
  <br>
- - **Usage:** /trade show
  - **Description:** Output all logs in memory
  - **Permission:** safetrade.show
  <br>
- - **Usage:** /trade send player-name
  - **Description:** Send the request to a player
  - **Permission:** safetrade.send
  <br>
- - **Usage:** /trade cancel
  - **Description:** Cancel the request
  - **Permission:** safetrade.cancel
  <br>
- - **Usage:** /trade accept
  - **Description:** Accept the request
  - **Permission:** safetrade.accept
  <br>
- - **Usage:** /trade deny
  - **Description:** Dent the request
  - **Permission:** safetrade.deny
  <br>
- - **Usage:** /trade ignore player-name
  - **Description:** Ignore a player's request
  - **Permission:** safetrade.ignore
## Config

### config.yml
```yml
#自动取消未被同意的请求时间 秒
cancel-second: 60
#是否要求前行
sneak-mode: true
#双方同意 取消的时长 秒
continue-second: 5
#记录日志
start-logs:
  enabled: true
  #是否在控制台输出每次交易的内容
  console-enabled: true
```

### lang.yml
```yml
no-find-trade: "&b[服务器娘]&c尚未查找到与此相关的交易"
trade-success: "&b[服务器娘]&a交易成功!"
trade-inventory-title: "安全交易"
reload-message: "&b[服务器娘]&a插件 &eSafeTrade &a重载成功喵~"
red-button-hover: "&c点击以结束准备"
green-button-hover: "&a点击以中断交易"
gray-frame-item-hover: "&e装饰线"
yellow-not-confirm: "&e等待双方结束准备"
yellow-confirm-duration: "&e完成倒计时 {duration} 秒"
send-fail-self: "&b[服务器娘]&c发起失败 &e%sender_name% &c还有一个尚未处理的交易请求"
send-fail-ignore: "&b[服务器娘]&c发起失败 &e{player} &c已屏蔽您的交易"
send-fail-other: "&b[服务器娘]&c发起失败 &e%receiver_name% &c还有一个尚未处理的交易请求"
send-success-self: "&b[服务器娘]&a成功向 &e%receiver_name% &a发起了交易请求 点击这条消息以取消"
hover-cancel: "&a点击这条消息以取消"
send-success-other: "&b[服务器娘]&a玩家 &e%sender_name% &a向您发起了交易请求 交易将于 &e%s% &a秒后取消"
give-to-sender-title: "请及时拿走物品"
give-to-receiver-title: "请及时拿走物品"
text-accept: "&a点击这条消息以接受"
hover-accept: "&a点击这条消息以接受"
text-deny: "&c点击这条消息以拒绝"
hover-deny: "&c点击这条消息以拒绝"
cancel-success: "&b[服务器娘]&a成功取消与玩家 &e%receiver_name% &a的交易请求"
cancel-fail: "&b[服务器娘]&c取消失败 未找到与玩家 &e%sender_name% &c相关的交易"
accept-fail: "&b[服务器娘]&c接受失败 未找到与玩家 &e%receiver_name% &c相关的交易"
accept-success-receiver: "&b[服务器娘]&a您接受了玩家 &e%sender_name% &a的交易请求"
accept-success-sender: "&b[服务器娘]&a玩家 &e%receiver_name% &a接受了您的交易请求"
deny-fail: "&b[服务器娘]&c拒绝失败 未找到与玩家 &e%receiver_name% &c相关的交易"
deny-success-sender: "&b[服务器娘]&c玩家 &e%receiver_name% &c拒绝了您的交易请求"
deny-success-receiver: "&b[服务器娘]&c您拒绝了玩家 &e%sender_name% &c的交易请求"
remove-ignore: "&b[服务器娘]&a您解除了对玩家 &e{player} &a的屏蔽"
add-ignore: "&b[服务器娘]&a您屏蔽了玩家的 &e{player} &a的交易"
```

## Contact Author

- qq: 1364596766
- website: https://www.torosamy.net

## License

[GPL-3.0 license](./LICENSE)

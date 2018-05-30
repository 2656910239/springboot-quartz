# springboot-quartz
springboot + quartz 实现定时调度任务

注：此demo的定时调度，只考虑了单服务器定时调度，如果将应用部署到多台服务器上，需要考虑同一个任务同时在两台服务器上调度的问题，可使用redis来实现，
思路为：任务key为任务id[可自行加前缀]，value为主机ip_id
1.从redis取key对应的值（以task的id为1，部署到192.168.0.1和192.168.0.2两台主机来举例，下面ip都是应用所在主机的ip）
  (1).如果没有值，则设置值为ip_id，sleep 5秒，再取，如果取到的值为本机ip_id则执行，否则不执行[说明在sleep期间另一台服务器修改过，让另外一台执行]
  (2).如果有值，并且值为自己ip_id，则执行，否则不执行
  (3).设置key的过期时间为1分钟，防止任务1在192.168.0.2上执行后，主机down了，redis中的value值一直为192.168.0.2_1从而导致192.168.0.1一直调度不了任务的问题。

以前公司的方案使用的是单服务器部署，通过shell脚本启动，传入不同的参数，如task组，同一个组中的task用同一个shell启动，然后用keepalive主备机模式来实现高可用的

本demo中提供了接口来动态调整定时任务的调度周期，还有一种方式是，单独写一个定时调度任务去扫描定时调度任务表，然后取出数据，与内存中的定时调度周期做比较，
当调度周期不同时，更新内存中的调度周期(如果直接修改了表中的调度周期可用此法，也可将此法与上面的提供接口的同时使用)


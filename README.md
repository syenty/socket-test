# socket-test

* 자료 출처 URL
	* https://i-hope9.github.io/2020/12/14/SpringBoot-Netty-2-SocketServer.html
	* https://myhappyman.tistory.com/172
	* https://sina-bro.tistory.com/15
	* https://javacoding.tistory.com/145?category=444597

```
* Channel, EventLoop, ChannelFuture
네티의 네트워킹 추상화를 대표한다고 말할 수 있는 Channel, EventLoop, ChannelFuture 클래스. 이 세 가지 개념의 기능들을 다음과 같이 요약해볼 수 있다.

Channel : 소켓(Socket)
EventLoop : 제어 흐름, 멀티스레딩, 동시성 제어
ChannelFuture : 비동기 알림
```

```
* Channel 인터페이스
자바 기반 네트워크에서 기본 구조는 Socket 클래스다. 네티의 Channel 인터페이스는 Socket으로 직접 작업할 때의 복잡성을 크게 완화하는 API를 제공한다.

ex) NioServerSocketChannel, LocalServerChannel, OioServerSocketChannel ...
```

```
* EventLoop 인터페이스
: EventLoop는 연결의 수명주기 중 발생하는 이벤트를 처리하는 네티의 핵심 추상화를 정의한다.
```

<br>
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FHwKiE%2FbtqQE9ArMgQ%2FfWRfQ9RZ0mvqkY8DrElkG1%2Fimg.jpg" />
* Channel, EventLoop, EventLoopGroup 간의 관계
<br><br>

```
EventLoopGroup은 하나 이상의 EventLoop를 포함한다.
EventLoopGroup은 Channel에 EventLoop를 할당
EventLoop는 수명주기 동안 하나의 Thread로 바인딩된다.
EventLoop에서 처리되는 모든 입출력 이벤트는 해당 전용 Thread에서 처리된다.
하나의 Channel은 수명주기 동안 하나의 EventLoop에 등록할 수 있다.
EventLoop를 하나 이상의 Channel로 할당할 수 있다
```

```
* ChannelFuture 인터페이스
네티의 모든 입출력 작업은 비동기적이다. 즉, 작업이 즉시 반환되지 않을 수 있으므로 나중에 결과를 확인하는 방법이 필요하다. 
이를 위해 네티는 ChannelFuture 인터페이스를 제공하며, 이 인터페이스의 addListener() 메서드는
작업이 완료되면 알림을 받을 ChannelFutureListener 하나를 등록한다.

ChannelFuture 인터페이스는 미래에 실행될 작업의 결과를 위한 표시자라고 생각할 수 있다. 
실행되는 시점은 여러 요소에 의해 좌우되므로 정확한 시점을 예측하기는 불가능하지만 실행된다는 점은 확실하다. 
또한 동일한 Channel에 속하는 모든 작업은 호출된 순서와 동일한 순서로 실행된다.
```








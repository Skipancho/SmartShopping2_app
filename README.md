# SmartShopping

## ⚙️ 주요 기능

1. 장바구니 및 체크리스트
    1. 마트에서 카트 내부의 상황을 손쉽게 파악할 수 있는 장바구니 기능
    2. 마트에 방문하기 전 미리 구매 목록을 작성할 수 있는 체크리스트 기능
2. 상품 검색 및 상품 정보 확인
    1. 구매하려는 상품을 미리 검색하고 다른 구매자의 리뷰 등의 상품 정보를 
3. 구매 내역 확인 및 지출 통계
    1. 과거의 구매 내역을 확인하고 리뷰를 작성 할 수 있는 구매내역 확인 기능
    2. 월별, 카테고리별 지출 통계를 그래프로 확인할 수 있는 지출 통계 기능

## 📱 Application Preview

<img width="742" alt="스크린샷 2023-02-07 오후 9 11 10" src="https://user-images.githubusercontent.com/79133730/217241592-c170edb5-4d25-4964-89ab-8e8d161dc1bc.png">

## 🛠 Stack

- Kotlin
- Coroutines
- Retrofit2 + Gson
- OkHttp3
- Room
- Coroutine Flow
- Dagger-Hilt
- JUnit
- Timber
- Spring-Boot
- Spring-Data-JPA
- Auth0-Java-JWT
- JBCrypt

## 📦 Package

android app

```
📂app
 ┣ 📂data
 ┃ ┣ 📂auth
 ┃ ┣ 📂local
 ┃ ┣ 📂remote
 ┃ ┗ 📂repository
 ┣ 📂di
 ┣ 📂domain
 ┃ ┣ 📂model
 ┃ ┣ 📂repository
 ┃ ┗ 📂usecase
 ┣ 📂presentaion
 ┃ ┣ 📂adapter
 ┃ ┣ 📂base
 ┃ ┣ 📂custom
 ┃ ┣ 📂decoration
 ┃ ┣ 📂ui
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┣ 📂mypage
 ┃ ┃ ┣ 📂product
 ┃ ┃ ┣ 📂registration
 ┃ ┃ ┣ 📂search
 ┃ ┃ ┣ 📂signin
 ┃ ┃ ┗ 📂signup
 ┃ ┗ 📂extension
 ┗ 📂common
```

server

```
📂smartshopping
 ┣ 📂common
 ┣ 📂domain
 ┃ ┣ 📂auth
 ┃ ┣ 📂repository
 ┃ ┣ 📂request
 ┃ ┣ 📂response
 ┃ ┗ 📂service
 ┣ 📂presentation
 ┃ ┣ 📂config
 ┃ ┣ 📂controller
 ┃ ┗ 📂interceptor
 ┗ 📂entity
```

## 🔗 Server Link
[GitHub - Skipancho/SmartShopping_server_kotlin](https://github.com/Skipancho/SmartShopping_server_kotlin)

## 📖 Presentation

[SmartShopping_ppt](https://www.miricanvas.com/v/11qfq3v)
<img width="1439" alt="스크린샷 2023-02-05 오후 7 59 36" src="https://user-images.githubusercontent.com/79133730/217241811-b9681a7a-3369-4a3c-b717-eca6e304282d.png">
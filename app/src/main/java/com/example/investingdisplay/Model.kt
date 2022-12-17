package com.example.investingdisplay

abstract class Model {
//    아래 부분처럼 오버라이드를 하고싶으면, 환율과 증시의 Facade 크롤러들이 같은 클래스를 상속받아야 할 것 같습니다
//    우선 본 클래스에서 정의하지 않는 방향으로 처리했습니다.
    abstract val date : String
    abstract fun setDataList()
}
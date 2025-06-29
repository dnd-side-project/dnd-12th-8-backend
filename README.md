
<div align="center">
  <img src="https://github.com/user-attachments/assets/b9b2ca4a-222d-4c3d-9d27-4e62d90a6a6a" width="600"/>
</div>


## 📝 **프로젝트 소개**

98%는 프로젝트를 만든 후 **2% 부족한 피드백**을 채워 100%를 완성하자는 의미로 기획된 **피드백 플랫폼**입니다.
객관식, 주관식, A/B 테스트, 척도형 등 다양한 질문을 생성하고 여러 사용자에게 답변을 받아 결과를 분석할 수 있습니다.

## **💡 문제 상황과 해결 방법**

1. **페이지네이션 조회 시 30초 이상의 응답 속도로 사용자 경험 저하되는 문제가 있었습니다.**
   
    ➡ WHERE, ORDER BY 조건으로 인한 테이블 풀 스캔이 오래 걸리고 있었습니다.
    

1. **대용량 데이터 환경에서 뒤쪽 페이지 조회 시 응답 속도가 점차 저하되었습니다.**
    
    ➡ OFFSET 기반 페이지네이션으로 이전 페이지 데이터를 모두 읽어야 하는 구조로 요청 페이지 번호가 클수록 응답 시간이 느려지는 문제였습니다.
    

문제 해결 경험기 : [페이지네이션 조회 성능 개선 과정](https://velog.io/@dnzp75/OFFSET-%ED%8E%98%EC%9D%B4%EC%A7%80%EB%84%A4%EC%9D%B4%EC%85%98-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94-%EC%BB%A4%EB%B2%84%EB%A7%81-%EC%9D%B8%EB%8D%B1%EC%8A%A4%EC%99%80-%EC%84%9C%EB%B8%8C%EC%BF%BC%EB%A6%AC-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0)

## **⚒️ Project Architecture**

![image](https://github.com/user-attachments/assets/f7266d74-8e6c-4087-aaac-26d69c514f40)

## ⚙ **개발 환경**

- **Backend**: Spring Boot, Java 17, Gradle, JPA, MySQL, Redis
- **Infra**: GitHub Actions, AWS


## **✅ Project Goal**

1. 요구사항을 해결하면서 문제를 발견하고 정의하며, 팀의 상황에 맞는 최선의 조건을 선택해 문제를 해결합니다.
2. 기술 스택을 사용하면서 해당 기술의 컨셉을 이해하고 사용하면서 어떻게 통합하여 사용하는지에 대한 경험을 쌓습니다.
3. 테스트 격리와 요구사항에 맞는 테스트 코드를 작성합니다.
4. 대용량 데이터 대비 인덱스 튜닝 프로그래밍으로 성능을 개선합니다.
5. 상대 팀원과 지속적으로 소통하며 빠르고 정확하게 기능을 구현 할 수 있도록 합니다.
6. 구체적이고 꾸준한 상호 피드백으로 "계속 협업 하고 싶은 팀원"이 될 수 있도록 노력합니다.

## **✍🏻 협업 제약조건**

- [Github-flow]에 따라 프로젝트에 대해 공동 작업을 수행합니다.(https://docs.github.com/ko/get-started/quickstart/github-flow)
- 코드리뷰가 끝난 작업은 반드시 상대 팀원의 APPROVE가 있어야 MERGE가 가능합니다.
- 이슈는 최대한 작은 작업 단위로 생성하고 추후 진행할 기능에 대해서는 반드시 팀원과 협의합니다.

## **👀 Code Convention**

- Google Java Code Convention 준수
- 링크 https://google.github.io/styleguide/javaguide.html 

## **🛠 Project UI/UX**

<img width="1933" alt="image" src="https://github.com/user-attachments/assets/fccafe2c-58a8-4e5c-b782-c039cb0b0b9f" />

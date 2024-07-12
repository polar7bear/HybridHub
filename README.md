# PharmaGo
# 사용자 위치 기반 약국 추천 서비스


## 프로젝트 소개
- 사용자의 위치를 기반으로, 10km 내에 가장 가까운 약국 3곳을 찾을 수 있습니다.
- 카카오 지도로 연동하여 길 찾기 및 로드맵도 이용할 수 있습니다.

## 개발 기간
2024.06.20 ~ 2024.07.11

### 구현 내용
- 사용자의 위치의 위도, 경도와 10km내의 가장 가까운 약국 3곳 각각의 위도, 경도로 하여금 haversine formula 알고리즘을 적용하여 최단 거리를 구현했습니다.
- 약국의 메타데이터나 위치 등은 불변하다고 생각하고 성능 향상을 고려해서 Redis를 사용하게 되었습니다.  
  - Redis와 데이터베이스 간에 약국 데이터를 동기화 시켜 만약 Redis 서버가 failover 날 경우 데이터베이스에서 약국정보를 조회해 오도록 구현했습니다. (약국 데이터는 공공데이터포털에서 csv로 가져와서 데이터베이스에 저장 해줬습니다)
- Docker compose를 활용 하기위해 로컬상에서 docker-compose-local.yml을 작성하여 다중 컨테이너 환경에서 개발을 하였고, 프로덕트 환경에서도 ec2 인스턴스내에서 다중 컨테이너로 애플리케이션, 데이터베이스, 레디스 서버를 띄웠습니다.
- 외부 라이브러리인 base62를 활용하여 shorten url을 구현했습니다.
  - 사용자가 주소를 입력하여 검색을 하면 약국 정보의 pk값을 인코딩하여 길 찾기 url을 http://localhost/find/3qa 와 같은 url로 보여주게 했고, 
여기서 3qa가 약국 정보의 pk값이며  /find/{} 로 요청을 하면 인코딩 된 pk값을 다시 디코딩하여 해당 pk를 가진 약국의 정보를 가져와 kakao 길찾기 url로 리다이렉트 해주도록 하였습니다.
- 모든 api와 데이터베이스 CRUD, 레디스 자료구조 및 CRUD, 서비스 코드 등에 대하여 테스트 코드를 작성했습니다.
- client view는 Thymeleaf를 활용하여 개발했습니다.

## 프로젝트 개발 환경 및 기술 스택

- **Language** : Java 21
- **Build** : Gradle
- **Framework** : Spring Boot 3.2.3
- **Library** : Spring Data JPA, Thymeleaf, base62
- **Database** : MariaDB
- **DevOps** : Docker, AWS EC2
- **Test** : Junit5, Mockito
- **Open API** : Kakao Map API
- **Data** : 공공데이터포털

<br>

## ERD 설계

<br>


<br>

## 서비스 아키텍처

## 시연 영상

https://github.com/user-attachments/assets/f7aef539-90a6-4c99-ac99-a09632984705




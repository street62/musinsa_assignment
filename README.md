# 카테고리 API - 최승리

안녕하세요. 최승리입니다.

사전 과제인 카테고리 API를 구현했습니다.

예외처리나 테스트 등 세부적인 부분에서 미흡한 지점들이 많지만, 우선 상위/하위 카테고리 연관관계를 바탕으로 테이블을 설계하고 카테고리 조회 로직을 구성하는 것이 가장 핵심이라고 판단해 해당 부분에 중점을 두고 구현을 진행했습니다.

이외 부분은 제출 제출 이후에도 계속 더 나은 방향성을 고민해 보고자 합니다. 잘 부탁드립니다!

## 실행 방법
과제 명세에 임베디드 DB 사용을 권장하셔서 로컬에서 H2를 사용해 개발했습니다.

리눅스 등에서 실행하실 때는 
1) 미리 빌드해 둔 [jar 파일을 다운로드](https://drive.google.com/file/d/19wsENRMBi1jN-ae9DzXfSMCzGfqeZJ2t/view?usp=sharing)해 터미널에 DB 정보 관련 변수를 명시해 주시거나
2) 소스코드의 `src/main/resources/application.yml` 파일을 직접 수정해 DB 정보를 추가하신 후 빌드하셔서 사용하시면 됩니다.

다운로드 받으신 jar 실행 시 변수를 명시하시려면 아래의 형식을 참고해주세요. DB 드라이버 의존성은 H2와 MySQL이 포함되어 있습니다.

````
java -jar -Ddriver=org.h2.Driver -Durl=jdbc:h2:tcp://localhost/~/musinsa -Dusername=sa -Dpassword= assignement.jar
````

## 주요 기능과 고려사항
- 스프링 부트와 Spring DATA JPA(Hibernate), H2 데이터베이스를 사용하여 구현했습니다.
- 복잡한 동적 쿼리가 필요하다고는 생각하지 않아 QueryDsl 등은 사용하지 않았고, DATA JPA 제공 메서드를 기반으로 JPQL을 통해 쿼리를 작성했습니다.

### 카테고리 조회
GET `/categories`

GET `/categories/{categoryId}`

- 카테고리 depth 별로 별도의 테이블을 만드는 것은 유연하지 못하다고 판단하여 CATEGORY 단일 테이블에서 자기 자신을 참조(조인)하는 방식으로 구현했습니다.
- 상위 카테고리를 나타내는 `parentCategory`를 @ManyToOne으로 매핑하고, 하위 카테고리 리스트인 subCategories는 @Transient 어노테이션을 통해 테이블과 매핑하지 않고 객체에서만 관리하도록 했습니다.
- 카테고리 조회 요청 시 fetch left join으로 전체 카테고리의 정보와 상위 카테고리를 함께 DB에서 가져오고, 이후 각 하위 카테고리 매핑은 서버에서 처리하도록 해 DB 통신을 최소화했습니다. 
  - 처음 구현 시에는 하위 테이블 리스트인 `subCategories`를 @OneToMany로 매핑하는 방식으로 구현하였는데요. 이 경우 JPA에서 join을 사용하지 않고 1) 카테고리 최초 로딩을 위한 SELECT 2) 상위 카테고리 조회를 위한 SELECT 3) 하위 카테고리 조회를 위한 SELECT로 나누어 각각 쿼리를 날리는 것을 확인할 수 있었습니다.
  - 이와 같은 방식은 상위 카테고리를 가져오는 쿼리도 분리되고, 무엇보다 하위 카테고리의 depth가 증가할 때마다 계속 DB와 통신해야 하므로 효율이 떨어질 것이라 판단해 현재의 구현 방식으로 변경했습니다.

### 카테고리 추가
POST `/categories`

- request
````json
{
   "name": "newCategoryName"
}
````

### 카테고리명 수정
PATCH `/categories/{categoryId}`

- request
````json
{
   "name": "modifiedCategoryName"
}
````

### 상위 카테고리 설정/변경
PATCH `/categories/{categoryId}/parent`
- request
````json
{
   "parentCategoryId": 1
}
````

### 카테고리 삭제
DELETE `/categories/{categoryId}`
- 실제 DB 레코드는 삭제하지 않고, `Category` 엔티티의 `markAsDeleted()` 메서드를 통해 `isDeleted` 필드를 true로 바꾸는 soft delete 방식으로 구현했습니다.
- `CategoryRepository`에서 카테고리 DB 조회 시 `isDeleted`가 false인 경우만 불러오도록 하였습니다.
- `markAsDeleted()` 메서드는 상위 카테고리 삭제 시 재귀적으로 하위 카테고리를 모두 삭제하는 로직으로 되어 있습니다.








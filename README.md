## MVC 패턴
## JSP 파일

### 로그인

컨트롤러: 

- (기존)loginProcess.jsp → LoginAction.java(logout 기능 합치기)
    
    아이디랑 패스워드 받아오기 (폼으로 넘겨받기) : request.setAttribute해서 넘김
    
    if문 써서 아이디랑 패스워드 일치하는지 확인하기 
    
    - loginCheck(id, pw) : DAO
        1. 일치 시 아이디를 request.setAttribute해서 main.jsp로 넘긴다 
        2. main 상단에 scssion.setAttribute 해서 아이디를 세션에 넣어준다
    - logoutAction
        
        
- joinProcess.jsp → JoinAction.java
    
    폼으로 회원정보 다 받아오기
    
    - insetUser(회원정보)
        - 회원정보는 JoinBean으로 만들어서 넘기기
    

뷰: 

- loginForm.jsp
    
    
- joinForm.jsp
    
    
- main.jsp
    
    네이게이션 바 형태
    
    - 로그인 버튼
    - 게시판 버튼
    - 장바구니 버튼

모델: 

- LoginDAO
    - loginCheck(): 아이디랑 패스워드 끌어오고 비교
- LoginBean
    - id
    - pw
- JoinDAO
    - insertUser(): 회원정보 다 inset into 해버리기
- JoinBean
    - id
    - pw
    - secondPw
    - mail
    - name
    - Idnum
    - birth
    - hobby
    - intro

### 장바구니

컨트롤러: 

- (기존)CartProcess.jsp
    
    BasketListAction: 장바구니 리스트 보여주기 
    
    - basketList(아이디)
    
    BasketAddAction: 장바구니 추가
    
    - basketAdd(아이디)
    
    BasketModifyAction: 장바구니 수정
    
    - basketModify(아이디)

뷰: 

- item.jsp(상품 보여주는 뷰)
- basket.jsp(장바구니 뷰)

모델: 

- BasketDAO
    - basketList(id)
    - basketAdd(id)
    - basketModify(id)
- BasketBean
    - 상품명
    - 상품가격
    - 구매수량

### 관리자

컨트롤러:

- AdminFrontController
    
    AdminListAction: 회원 리스트 보여주기 
    
    - adminList()
    
    AdminDetailAction: 회원 리스트 상세보기
    
    - adminDetail(아이디)
    
    AdminDeleteAction: 회원 리스트 삭제
    
    - adminDelete(아이디)

뷰: 

- adminList.jsp : 회원 전체 보기
    
    
- adminDetail.jsp : 회원상세보기
- adminDelete.jsp : 회원삭제하기

모델:

- AdminDAO
    - adminList()
    - adminDetail(아이디)
    - adminDelete(아이디)
- AdminBean

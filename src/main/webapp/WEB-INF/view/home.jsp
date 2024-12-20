<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>首頁</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		<link rel="stylesheet" href="/ticket/css/layout.css">
	</head>
	<body>
    <section id="sec1">
        <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
            <div class="container-xl">
                <a class="navbar-brand" href="/ticket/home">Ticket</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent" style="justify-content: space-between">
                    <div>
                        <ul class="navbar-nav me-auto mb-2 mb-md-0">

                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#sec1">TOP</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link text-white" href="/ticket/user/view">會員資訊</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link text-white" href="/ticket/user/order">歷史訂單</a>
                            </li>
                        </ul>
                    </div>

                    <div>
                        <form class="d-flex">
                            <input class="form-control me-2" type="search" placeholder="搜尋活動" aria-label="Search">
                            <button class="btn btn-success" style="display: inline; white-space: nowrap;" type="submit">搜尋</button>
                        </form>
                    </div>

                    <div>
                        <ul class="navbar-nav me-auto mb-2 mb-md-0">
                            
                            <c:if test="${ login == 0 }">
	                            <li class="nav-item">
	                                <a class="nav-link text-white" href="/ticket/login">登入</a>
	                            </li>
							</c:if>
							
							<c:if test="${ login == 1 }">
							
								<li class="nav-item">
	                                <a class="nav-link text-white" href="#">${ userName } |</a>
	                            </li>
							
	                            <li class="nav-item">
	                                <a class="nav-link text-white" href="/ticket/logout">登出</a>
	                            </li>
							</c:if>
							
                        </ul>
                    </div>
                </div>
            </div>
        </nav>
    </section>
    
    <header style="width: 100%; background-color: #000;">
	    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="margin-top: 56px;">
		  <div class="carousel-indicators">
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
		  </div>
		  <div class="carousel-inner">
	        <div class="carousel-item active" data-bs-interval="6000">
	            <a href="<c:url value='/event/view?eventId=${mainEventId}' />">
	                <img src="<c:url value='/image?id=${mainEventId}&timestamp=${System.currentTimeMillis()}' />" class="main-image" alt="Event Image" />
	            </a>
	        </div>
	
			<c:forEach var="eventId" items="${randomEventIds}">
			    <div class="carousel-item" data-bs-interval="10000">
			    	<a href="<c:url value='/event/view?eventId=${eventId}' />">
			        	<img src="<c:url value='/image?id=${eventId}&timestamp=${System.currentTimeMillis()}' />" class="main-image" alt="Event Image" />
			    	</a>
			    </div>
			</c:forEach>
		  </div>
		  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
		  </button>
		  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Next</span>
		  </button>
		</div>
    </header>
	
	<div class="row">
		<c:forEach var="homeEventDtos" items="${homeEventDtos}">
            <div class="col-lg-3 p-3 card">
                <a href="<c:url value='/event/view?eventId=${homeEventDtos.eventId}' />">
                    <div class="border rounded h-100">
                        <img src="<c:url value='/image?id=${homeEventDtos.eventId}&timestamp=${System.currentTimeMillis()}' />" class="home-image" alt="Event Image" />
                        <div class="card-body">
                            <h5 class="card-title fw-bold link-dark">${homeEventDtos.eventName}</h5>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                </a>
            </div>
		</c:forEach>
	</div>
	
	
    <!-- 至JSDeliver網站搜Bootstrap5取得 -->	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
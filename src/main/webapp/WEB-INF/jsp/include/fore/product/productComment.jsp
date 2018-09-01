<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	
<div class="productReviewDiv" >
	<div class="productReviewTopPart">
		<a  href="#nowhere" class="productReviewTopPartSelectedLink">商品详情</a>
		<a  href="#nowhere" class="selected">累计评价 <span class="productReviewTopReviewLinkNumber">${p.commentnum}</span> </a>
	</div>
	
		
	<div class="productReviewContentPart">
		<c:forEach items="${comments}" var="comment">
		<div class="productReviewItem">
		
			<div class="productReviewItemDesc">
				<div class="productReviewItemContent">
					${comment.content }
				</div>
				<div class="productReviewItemDate"><fmt:formatDate value="${comment.createtime}" pattern="yyyy-MM-dd"/></div>
			</div>
			<div class="productReviewItemUserInfo">
				${comment.user.anonymousName}
                    <span class="userInfoGrayPart">（匿名）</span>
			</div>
			
			<div style="clear:both"></div>
		
		</div>
		</c:forEach>
	</div>

</div>

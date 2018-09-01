<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/8/22
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    $(function () {
        $("ul.pagination li.disabled a").click(function(){
            return false;
        });
    });
</script>

<nav>
    <ul class="pagination">
        <li <c:if test="${page.start==0}">class="disabled"</c:if>>
            <a href="?start=0${page.param}" aria-label="Previouts">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <li <c:if test="${page.start==0}">class="disabled"</c:if>>
            <a href="?start=${page.start - page.count}${page.param}" aria-label="Previous" >
                <span aria-hidden="true">‹</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
            <li <c:if test="${status.index * page.count == page.start}">class="disabled" </c:if>>
            <a href="?start=${status.index * page.count}${page.param}"
               <c:if test="${status.index * page.count == page.start}">class="current" </c:if>>
                    ${status.count}</a>
            </li>
        </c:forEach>

        <li <c:if test="${page.start == page.last}">class="disabled"</c:if>>
            <a href="?start=${page.start + page.count}${page.param}" aria-label="Next">
                <span aria-hidden="true">›</span>
            </a>
        </li>
        <li <c:if test="${page.start == page.last}">class="disabled"</c:if>>
            <a href="?start=${page.last}${page.param}" aria-label="Next">
                <span aria-hidden="true">»</span>
            </a>
        </li>
    </ul>
</nav>

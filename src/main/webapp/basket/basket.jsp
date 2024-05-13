<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.basket.db.*" %>

<% 	List<BasketBean> basketList=(List<BasketBean>)request.getAttribute("basketlist");
	int index = 0;
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<%@ include file="../login/NavBar.jsp" %>
<style>
    table {
        width: 80%;
        margin: 20px auto;
        border-collapse: collapse;
        text-align: left;
    }
    th, td {
        padding: 8px;
        border: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
    }
    input[type="button"] {
        width: 30px;
    }
    .submit{
    	float: right;
    	margin: auto 100px;
    }
</style>
<script>
	function updateQuantity(index, isPlus){ // +, - 따라 장바구니 수량 조절하기 
		var qtyInput = document.getElementById("quantity"+index);
		var item_total = parseInt(qtyInput.value);
		console.log("item_total은 "+item_total);
		
		if(isPlus){
			item_total++;
		}else{
			if(item_total>1){
				item_total--;
			}
		}
		
		var item = 'item_name'+index;
		var item_name = document.getElementById(item).value;
		console.log("item_name은 "+item_name);
		console.log("변경된 item_total은 "+item_total);
		
		qtyInput.value = item_total;
		updateTotal(index);
	}
	
	function updateTotal(index){ // 각 상품별 합계 조절하기 
		var qty = parseInt(document.getElementById('quantity'+index).value);
		var price = parseFloat(document.getElementById('price'+index).innerText.replace('원', ''));
		var total = qty*price;
		document.getElementById('total'+index).innerText = total.toLocaleString()+'원';
		calculateTotalSum();
	}
	
	function calculateTotalSum(){ // 모든 상품 최종 합계 구하기 
		var rows = document.querySelectorAll('.row-item');
		var totalSum = 0;
		rows.forEach(function(row){
			var total = parseFloat(row.querySelector('.item-total').innerText.replace('원', '').replace(/,/g, ''));
			totalSum += total;
		});
		document.getElementById('finalTotal').innerText = totalSum.toLocaleString()+'원';
	}
</script>
</head>
<body>
<h2 style="text-align:center;">장바구니 페이지</h2>
<form action="./BasketModify.ba" method="post">
	<table>
	    <tr>
	        <th>상품명</th>
	        <th>가격</th>
	        <th>수량</th>
	        <th>합계</th>
	       
	    </tr>
<%
	
	for(BasketBean cart : basketList){
		
%>
	
	    <tr class="row-item">
	        <td>
	        	<%=cart.getItem_name() %>
	        	<input type="hidden" id="item_name<%=index %>" name="item_name" value="<%=cart.getItem_name() %>" />
	        </td>
	        <td name="item_price" id="price<%=index%>"><%=cart.getItem_price() %>원</td>
	        <td>
	        	<input type="button" value="-" onclick="updateQuantity(<%=index %>, false)" />
	        	<input name="item_total" type="text" id="quantity<%=index %>" value="<%=cart.getItem_total() %> " readonly style="width:40px; text-align:center;" />
	        	<input type="button" value="+" onclick="updateQuantity(<%=index %>, true)" />
	        </td>
	        <%
	        int price = Integer.parseInt(cart.getItem_price());
			int quantity = Integer.parseInt(cart.getItem_total().trim());
			int total = price * quantity; 
			%>
	        <td id="total<%=index%>" class="item-total"><%=total%></td>
	        
	    </tr>
	<%
		index++;
		}
	%>
		<tr>
			<td colspan="3" style="text-align:right;">최종합계: </td>
			<td id="finalTotal"></td>
		</tr>
	</table>
	<button class="submit" onclick="submitBasket(this.form)">최종 결제하기</button>
</form>

<script>
	window.onload = function(){
		var rows = document.querySelectorAll('.row-item');
		rows.forEach(function(row, index){
			updateTotal(index);
		});
	};
	
	function submitBasket(form){
		alert("최종 결제를 진행합니다.");
		form.submit();
		
	}
</script>

</body>
</html>
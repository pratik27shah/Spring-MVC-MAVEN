<%@ page contentType = "text/html; charset = UTF-8" %>
<%@include file= "header.html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<style>
.databutton
{
  text-transform: uppercase;
  letter-spacing: 2px;
  text-align: center;
  color: #0C5;

  font-size: 24px;
  font-family: "Nunito", sans-serif;
  font-weight: 300;
  
  /*margin: 5em auto;
  padding: 20px 0;
  width: 29%;
  height:3%;
*/
  background: #0D6;
  border: 1px solid #0D6;
  color: #FFF;
  overflow: hidden;
  
  transition: all 0.5s;
}

.databutton:hover, .databutton:active 
{
  text-decoration: none;
  color: #0C5;
  border-color: #0C5;
  background: #FFF;
}

.databutton span 
{
  display: inline-block;
  position: relative;
  padding-right: 0;
  
  transition: padding-right 0.5s;
}

.databutton span:after 
{
  content: ' ';  
  position: absolute;
  top: 0;
  right: -18px;
  opacity: 0;
  width: 10px;
  height: 10px;
  margin-top: -10px;

  background: rgba(0, 0, 0, 0);
  border: 3px solid #FFF;
  border-top: none;
  border-right: none;

  transition: opacity 0.5s, top 0.5s, right 0.5s;
  transform: rotate(-45deg);
}

.databutton:hover span, .databutton:active span 
{
  padding-right: 30px;
}

.databutton:hover span:after, .databutton:active span:after 
{
  transition: opacity 0.5s, top 0.5s, right 0.5s;
  opacity: 1;
  border-color: #0C5;
  right: 0;
  top: 50%;
}

</style>

<!-- 
        <jsp:plugin align="middle" height="500" width="500" type="applet"  code="helloapplet.class"  
name="clock" codebase="."/>-->
<table class="table" style="width:88%">
        <thead>
          <tr>
            <th>File Name in Storage</th>
             </tr>
        </thead>
        <tbody>

           <c:forEach var="listValue" items="${filelist}">
            <tr>
              <td><label >${listValue}</label></td>

            </tr>
          </c:forEach>
 
        </tbody>
      </table>




<form action="update" method="get" style="position:relative;left:18%">
<input type="text" name="files"  style="width:40%" class=" required form-control" placeholder="Enter File path" id="openfilelog">
<input type="submit" onclick="storefile()" class="databutton"  value="Click to Store File"></input>
</form>
<label style="color:red">
${status}</label>
<br>
Click to View->:
<a href='${url}'>
${url}</a>

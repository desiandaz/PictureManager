<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>Categories</title>
  </head>
  <body>
    <h1>Categories</h1>
    <c:choose>
      <c:when test="${fn:length(pictureList) == 0}">
        <p>No pictures in this event</p>
      </c:when>
      <c:otherwise>
        <table>
          <thead>
            <tr>
              <th class="number">Id</th>
              <th>Name</th>
              <th>Storage Location</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${pictureList}" var="picture">
	           <tr>
                <td class="number">${picture.id}</td>
                <td>${picture.name}</td>
                <td>${picture.pictureStorageLocation}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:otherwise>
    </c:choose>
  </body>
</html>
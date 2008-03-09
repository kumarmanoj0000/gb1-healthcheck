<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!--
  | Cannot use the s:url tag here because it prepends the context root to the generated URL
  | and so does the c:redirect tag, resulting in an invalid redirect URL.
  -->
<c:redirect url="/workbench/show.go" />

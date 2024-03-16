<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Reservations
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="/rentmanager/rents/create">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="car" class="col-sm-2 control-label">Voiture</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="car" name="car">
                                            <c:forEach items="${cars}" var="car">
                                                <option value="${car.id}">${car.constructeur} ${car.modele}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client">
                                            <c:forEach items="${clients}" var="client">
                                                <option value="${client.ID}">${client.prenom} ${client.nom}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Date de debut</label>

                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="begin" name="begin">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">Date de fin</label>

                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="end" name="end">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });

    document.addEventListener('DOMContentLoaded', function() {
        var form = document.querySelector('.form-horizontal');
        var beginInput = document.getElementById('begin');
        var endInput = document.getElementById('end');

        form.addEventListener('submit', function(event) {
        var beginDate = new Date(beginInput.value);
        var endDate = new Date(endInput.value);

        // Calcul de la différence entre les dates en millisecondes
        var difference = endDate - beginDate;
        // Convertir la différence en jours
        var differenceInDays = difference / (1000 * 60 * 60 * 24);

        // Vérifier si la durée de réservation est supérieure à 7 jours
        if (differenceInDays > 7) {
        alert('La durée de réservation ne peut pas dépasser 7 jours.');
        event.preventDefault(); // Empêche la soumission du formulaire
        }
        });
    });
</script>
</body>
</html>

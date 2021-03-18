function pi_load() {
    $('#fechaalquiler, #fechafinalquiler').datepicker({
        showOn: 'button',
        buttonImage: 'images/calendar.gif',
        buttonImageOnly: true
    });

    // jQuery
    $(".parainfo thead").addClass("ui-widget-header");
     
    // CRUD
    $(".parainfo thead .crud .ins").tooltip().attr("title", "Añadir registro");
    $(".parainfo thead .crud .upd").tooltip().attr("title", "Actualizar registro");
    
    $(".parainfo thead .crud .del span").addClass("ui-icon ui-icon-minusthick");
    $(".parainfo thead .crud .upd span").addClass("ui-icon ui-icon-check");
    
    // DEL
    $(".crud .del").click(function() {
        var ids = [];

        $("input[name='_del']:checked").each(function() {
            ids.push($(this).val());
        });

        if(ids.length == 0) {
            alert("Debe seleccionar fila(s) a Retirar");
        } else {
            if(confirm("¿Retirar fila(s)?")) {
                window.location = "Autos?accion=DEL_ALQ&ids="+ids.toString();
            }
        }
    });
    
    // UPD
    $("#dupd").dialog({
        modal: true,
        autoOpen: false
    });
    //Mensaje de confirmación
    
    $(".crud .upd").click(function() {
        var id = $("input[name='_upd']:checked").val();

        if(isNaN(id)) {
            alert("Debe seleccionar Fila para Actualizar Datos");
        } else {
            if(confirm("¿Actualizar registro?")) {
                $.ajax({
                    url: "Autos?accion=GET_ALQ&id="+id,
                    success: function(data) {
                        var dato = data.split("+++");
                        
                        if(dato.length == 5) {
                            $("#idalquiler").val(dato[0]);
                            $("#fechaalquiler").val(dato[1]);
                            $("#fechafinalquiler").val(dato[2]);
                            $("#horainicio").val(dato[3]);
                            $("#horafin").val(dato[4]);
                        
                            $("#dupd").dialog('open');
                        }
                    }
                });
            }
        }
    });
    
    // botones
    $("input[type=submit]").button();
}

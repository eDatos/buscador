        function getParameterByName(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }
        
        function restorePreviousSectionFilter() {
            var section = getParameterByName("filtroSeccion");
            if (section == "") {
                $("#filtroSeccionArea").select2("val", null);
                $("#filtroSeccionOperacion").select2("val", null);
                $("#filtroSeccion").select2("val", null);
            }
            else if (section == 'SUBJECT_AREA') {
                $("#filtroSeccionOperacion").select2("val", null);
                swapSection(section);
            }
            else if (section == 'SURVEY') {
                $("#filtroSeccionArea").select2("val", null);
                swapSection(section);
            }
        }
        
        function swapSection(section) {
            if (section == 'SUBJECT_AREA') {
                $("#filtroSeccionOperacionContainer").hide();
                $("#filtroSeccionAreaContainer").show();
            }
            else if (section == 'SURVEY') {
                $("#filtroSeccionAreaContainer").hide();
                $("#filtroSeccionOperacionContainer").show();
            }
        }

		$(document).ready(function(){    
		    
		   // Use the each() method to gain access to each elements attributes
		    $('#bloq_interior a[rel]').each(function() {
                $(this).qtip({
                    content: {
                        text: function(event, api) {
                            $.ajax({
                                url: $(this).attr('rel'), // Use the rel attribute of each element for the url to load
                            })
                            .then(function(content){
                                api.set('content.text', content);
                            },
                            function(xhr, status, error) {
                                // Upon failure... set the tooltip content to the status and error value
                                api.set('content.text', status + ': ' + error);
                            });

                            return 'Loading...';
                        },
                        title: {
                            text: 'Lista de categorías',
                            button: 'Cerrar' // Show a close link in the title
                         }
                    },
                    position: {
                        my: 'top center'// In center
                    },
                    show: { 
                        event: 'click', // Open when click
                        solo: true // Only show one tooltip at a time
                    },
                    hide: {
                        event: 'click' // Hide on click
                    },
                    style: {
                        classes: 'qtip-light',
                        width: '700'
                    }
                });
            });
			   
		   // Pinchar en mas esta recargando la pagina, hay que evitarlo
		   $('#bloq_interior a[rel]').click (function (e) {
			  e.preventDefault();
		   });
		   
		   // Clearable input
		   function tog(v){return v?'addClass':'removeClass';} 
		   $(document).on('input', '#userQuery', function(){
		     $(this)[tog(this.value)]('x');
		   }).on('mousemove', '.x', function( e ){
		     $(this)[tog(this.offsetWidth-18 < e.clientX-this.getBoundingClientRect().left)]('onX');   
		   }).on('click', '.onX', function(){
		     $(this).removeClass('x onX').val('').change();
		   });
		   
		   // Item Results Details
		   $("#btnExpandInfo").click(function(){
		        $('.resultado_item').not('.resultado_item_nm_type').toggle();
		        $("#iExpandInfo").toggleClass("glyphicon glyphicon-eye-open glyphicon glyphicon-eye-close");
		        $("#btnExpandInfo span").toggle();
		        var bs_detail = ($("#iExpandInfo").attr("class").indexOf("glyphicon-eye-open") > -1) ? 1 : 0;
		        $.cookie("bs_detail", bs_detail, {path: "/", expires: 1});
		    });
		   
		   if ($.cookie("bs_detail") == 1) {
		       $("#btnExpandInfo").trigger("click");
		   }
		   
		   // Advanced Search: Open (don't use data-toggle="dropdown" of bootstrap)
		   $('#openAdvanceSearch').on('click', function(event) {
		       $(this).parent().toggleClass('open');
		       $('#filtroTextoQuery').val( $('#busqueda #userQuery').val());
		   });
		   
		   $("#filtroTexto").select2({
		       language: "es",
		       theme: "bootstrap",
		       width : "style",
		       minimumResultsForSearch: Infinity
		   });
		   
		   $("#filtroSeccion").select2({
		       language: "es",
		       placeholder: "Seleccione una opción",
		       theme: "bootstrap",
		       width : "style",
		       minimumResultsForSearch: Infinity, 
		       allowClear: true
		   })
		   .on("select2:unselecting", function() {
               if ($(this).val() == 'SUBJECT_AREA') {
                   $("#filtroSeccionArea").select2("val", null);
               }
               else if ($(this).val() == 'SURVEY') {
                   $("#filtroSeccionOperacion").select2("val", null);
               }
           });
		   
           $("#filtroSeccionArea").select2({
               language: "es",
               placeholder: "Seleccione una opción",
               theme: "bootstrap",
               width : "style"
               
           });
		   
           $("#filtroSeccionOperacion").select2({
               language: "es",
               placeholder: "Seleccione una opción",
               theme: "bootstrap",
               width : "style"
           });
		   
		   $("#filtroSeccion" ).change(function() {
		       swapSection($(this).val());
		   });
		   
		   // Advanced Search: Control Advance o Normal Search         
           $("#busqueda").submit(function(event) {
               if ($(".dropdown.dropdown-lg.open").length == 1) {
                   var input = $("<input>").attr("type", "hidden").attr("name", "searchType").val("adv");
                   $("#busqueda").append($(input));
                   $('#busqueda #userQuery').val($('#filtroTextoQuery').val());
                   // Validation form
                   if ($("#filtroSeccion").select2("val") === "SUBJECT_AREA") {
                       if ($("#filtroSeccionArea").select2("val") === null) {
                           $("#filtroSeccion").select2("val", null);
                       }
                   }
                   else if ($("#filtroSeccion").select2("val") === "SURVEY") {
                       if ($("#filtroSeccionOperacion").select2("val") === null) {
                           $("#filtroSeccion").select2("val", null);                           
                       }
                   }
               } else {
                   $('#search-adv').remove();
               }
           });
           
           // Advanced Search: Close (don't use data-toggle="dropdown" of bootstrap)
           $(document).on('click', function (e) {
               if (!$('.dropdown, .dropdown-lg').is(e.target) 
                       && $('.dropdown, .dropdown-lg').has(e.target).length === 0 
                       && $('.open').has(e.target).length === 0
               ) {
                   $("#filtroSeccionArea").select2("close");
                   $("#filtroSeccionOperacion").select2("close");
                   $('.dropdown, .dropdown-lg').removeClass('open');
               }              
           });

           restorePreviousSectionFilter();
           
		});

		

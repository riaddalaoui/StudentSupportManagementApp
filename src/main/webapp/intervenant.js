var historyData = {};
var enSoutien = 0;
var currentSoutien = {};

$(document).ready(function () {
    verifyIntervenantConnected();
    checkNewSoutien();
    setInterval(checkNewSoutien, 60000); // Check for new soutien every 60 seconds
});

function verifyIntervenantConnected() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'verifyIntervenantConnected'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (response.connected) {
            enSoutien = response.status;
            getHistory();
            if (enSoutien == 1) {
                currentSoutien = response.soutien;
                afficherSoutien();
            }
        } else {
            document.location.href = "login.html";
        }
    })
    .fail(function (error) {
        alert("Erreur lors de l'appel AJAX");
    });
}

function getHistory() {
    $('#historique-container').show(); // Show the history container
    $('#séance-container').show(); // Show the session container
    $('#content').hide(); // Hide the statistics content
    $('#soutien-container').hide(); // Hide the soutien container
    $('#tableau-de-bord-container').hide(); // Hide the tableau de bord container
    $('#menu button').removeClass('active'); // Remove active class from all buttons
    $('#boutonHistorique').addClass('active'); // Add active class to the history button

    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'getHistoryIntervenant'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (response) {
            historyData = response;
            displayHistory(response);
        } else {
            $('#historique').html("Erreur lors de la récupération de l'historique");
        }
    })
    .fail(function (error) {
        $('#historique').html("Erreur lors de l'appel AJAX");
    });
}

function displayHistory(history) {
    var histHtml = '';
    for (var key in history) {
        histHtml += '<div class="historique-item" id="' + key + '" onclick="selectHistoriqueItem(this); loadSoutien(\''+ key + '\')">' + history[key].date + ' ' + history[key].matière + '</div>';
    }
    $('#historique').html(histHtml);
    $('#séance').html('');
}

function selectHistoriqueItem(element) {
    $('.historique-item').removeClass('selected-historique-item');
    $(element).addClass('selected-historique-item');
}

function loadSoutien(key) {
    var session = historyData[key];
    var sessionHtml = '<div class="séance-info">' +
                          '<div class="séance-info-left">' +
                              '<label for="matiere">Matière</label><br>' +
                              '<input type="text" id="matiere" value="' + session.matière + '" readonly><br><br>' +
                              '<label for="description">Description</label><br>' +
                              '<textarea id="description" rows="4" cols="50" readonly>' + session.description + '</textarea><br><br>' +
                          '</div>' +
                          '<div class="séance-info-right">' +
                              '<label for="eleve">Élève</label><br>' +
                              '<input type="text" id="eleve" value="' + session.eleveNom + ' ' + session.elevePrenom + '" readonly><br><br>' +
                              '<label for="bilan">Bilan</label><br>' +
                              '<textarea id="bilan" rows="4" cols="50" readonly>' + session.Bilan + '</textarea><br><br>' +
                          '</div>' +
                          '<div class="séance-info-divider"></div>' +
                      '</div>' +
                      '<div id="auto-evaluation-container">' +
                          '<label for="auto-evaluation">Auto-évaluation :</label><br>' +
                          '<div id="auto-evaluation">';

    var smileyImg = '';
    if (session.autoEval == 1) {
        smileyImg = '<img src="Images/smileyBad.PNG" alt="nul" class="selected-smiley">';
    } else if (session.autoEval == 2) {
        smileyImg = '<img src="Images/smileyMid.PNG" alt="moyen" class="selected-smiley">';
    } else if (session.autoEval == 3) {
        smileyImg = '<img src="Images/smileyGood.PNG" alt="bien" class="selected-smiley">';
    }

    sessionHtml += smileyImg + '</div></div>';
    $('#séance').html(sessionHtml);
}

function checkNewSoutien() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'getSoutien'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (response.confirmation) {
            $('#notificationBadge').show();
            $('#notificationBadge').text(1); // Affiche "1" sur la cloche de notification
        } else {
            $('#notificationBadge').hide();
        }
    })
    .fail(function (error) {
        console.log('Error checking new soutien', error);
    });
}

function getStatistics() {
    $('#historique-container').hide(); // Hide the history container
    $('#séance-container').hide(); // Hide the session container
    $('#soutien-container').hide(); // Hide the soutien container
    $('#content').show(); // Show the statistics content
    $('#menu button').removeClass('active'); // Remove active class from all buttons
    $('#boutonStatistics').addClass('active'); // Add active class to the statistics button

    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'getStatistics'
        },
        dataType: 'json'
    })
    .done(function (response) {
        initMap(response);
        displayRepartition();
    })
    .fail(function (error) {
        alert("Erreur lors du chargement des statistiques");
    });
}

function initMap(data) {
    var map = L.map('map').setView([46.603354, 1.888334], 6); // Vue centrée sur la France
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    for (var coord in data) {
        var etablissement = data[coord];
        var latLng = coord.split(',');
        var marker = L.marker([latLng[0], latLng[1]]).addTo(map);
        marker.etablissementId = etablissement.id;
        marker.etablissementCode = etablissement.code;
        marker.etablissementIps = etablissement.IPS;
        marker.on('click', function(e) {
            calculStat(this);
        });
    }
}

function calculStat(marker) {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'calculStat',
            id: marker.etablissementId,
            code: marker.etablissementCode,
            ips: marker.etablissementIps
        },
        dataType: 'json'
    })
    .done(function (response) {
        var stat = response;
        var popupContent = "Lycée : " + marker.etablissementCode + "<br>IPS : " + marker.etablissementIps + 
                           "<br>Nombre de cours : " + stat.nombreDeCours + 
                           "<br>Note moyenne : " + stat.noteMoyenne + 
                           "<br>Meilleur intervenant : " + stat.meilleurIntervenantNom + " " + stat.meilleurIntervenantPrenom;
        L.popup()
            .setLatLng(marker.getLatLng())
            .setContent(popupContent)
            .openOn(marker._map);
    })
    .fail(function (error) {
        alert("Erreur lors de l'appel AJAX");
    });
}


function displayRepartition() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'displayRepartition'
        },
        dataType: 'json'
    })
    .done(function (response) {
        var repartitionData = [
            { name: 'Terminale', y: response["0"] },
            { name: 'Première', y: response["1"] },
            { name: 'Troisième', y: response["2"] },
            { name: 'Seconde', y: response["3"] },
            { name: 'Quatrième', y: response["4"] },
            { name: 'Cinquième', y: response["5"] },
            { name: 'Sixième', y: response["6"] }
        ];

        // Créer le graphique avec Highcharts
        var chart = Highcharts.chart('repartitionChart', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Répartition des élèves par classe',
                align: 'left'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            accessibility: {
                point: {
                    valueSuffix: '%'
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: 'Classes',
                colorByPoint: true,
                data: repartitionData
            }]
        });

        // Si vous avez besoin de personnaliser la légende manuellement, vous pouvez le faire ici.
        // document.getElementById('chart-legend').innerHTML = chart.generateLegend(); // Cette ligne est commentée car `generateLegend` n'existe pas.
    })
    .fail(function (error) {
        alert("Erreur lors du chargement de la répartition des élèves");
    });
}

$(document).ready(function() {
    displayRepartition();
});



function getSoutien() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'getSoutien'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (response.confirmation) {
            currentSoutien = response.soutien;
            enSoutien = 1;
            afficherSoutien();
        } else {
            Swal.fire({
                title: 'Pas de soutien en cours',
                icon: 'info',
                confirmButtonText: 'OK'
            });
        }
    })
    .fail(function (error) {
        console.log('Error', error);
        alert("Erreur lors de l'appel AJAX");
    });
}

function afficherSoutien() {
    var html = '<div id="demande-soutien" class="séance-container">' +
               '<h2><span class="soutien-label">Nom:</span></h2><p> ' + currentSoutien.eleveNom + ' ' + currentSoutien.elevePrenom + '</p>' +
               '<h2><span class="soutien-label">Matière:</span></h2><p> ' + currentSoutien.matière + '</p>' +
               '<h2 class="soutien-label">Description:</h2>' +
               '<p class="soutien-description">' + currentSoutien.description + '</p>' +
               '<div class="soutien-button-container"><button id="lancerSoutien" onclick="lancerSoutien()">Commencer</button></div>' +
               '</div>';
    $('#historique-container').hide();
    $('#séance-container').hide();
    $('#soutien-container').html(html).show();
    $('#content').hide();
    $('#tableau-de-bord-container').hide();
}

function lancerSoutien() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'demarrerSoutien'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (response.confirmation) {
            $('#soutien-container').html("<div id='demande-soutien'><img src='Images/professeur-devant-tableau.jpg' alt='visio icon'><div class='soutien-button-container'><button onclick='finirSoutien()'>Raccrocher</button></div></div>");
            disableButtons(); // Disable buttons when the session starts
        } else {
            alert("Erreur lors du lancement de l'appel");
        }
    })
    .fail(function (error) {
        alert("Erreur lors de l'appel AJAX");
    });
}

function finirSoutien() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'terminerVisio'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (response.confirmation) {
            bilan();
            enableButtons();
            $('#soutien-container').empty().hide();
        } else {
            alert("Erreur lors de la terminaison de l'appel");
        }
    })
    .fail(function (error) {
        alert("Erreur lors de l'appel AJAX");
    });
}

function bilan() {
    var modal = document.getElementById("modal");

    modal.style.display = "block";
    window.onclick = null;

    document.getElementById("bilanForm").onsubmit = function(event) {
        event.preventDefault();
        var bilan = $('#bilan').val();
        $.ajax({
            url: './ActionServlet',
            method: 'POST',
            data: {
                todo: 'bilan',
                bilan: bilan
            },
            dataType: 'json'
        })
        .done(function (response) {
            if (response.confirmation) {
                modal.style.display = "none";
                enSoutien = 0;
                $('#soutien-container').empty().hide();
                getHistory();
            } else {
                alert("Erreur lors de la notation");
            }
        })
        .fail(function (error) {
            alert("Erreur lors de l'appel AJAX");
        });
    }
}

function disconnect() {
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            todo: 'disconnect'
        },
        dataType: 'json'
    })
    .always(function () {
        document.location.href = "index.html";
    });
}

function disableButtons() {
    $('#boutonHistorique').prop('disabled', true);
    $('#boutonStatistics').prop('disabled', true);
}

function enableButtons() {
    $('#boutonHistorique').prop('disabled', false);
    $('#boutonStatistics').prop('disabled', false);
}

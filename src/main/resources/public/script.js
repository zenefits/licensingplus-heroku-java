var currMonth = null;
var currYear = null;
var calendar = null;
var statusData = null;

$(function() {
  var date = new Date();
  currMonth = date.getMonth();
  currYear = date.getFullYear();
  $("#prevMonth").click(prevMonth);
  $("#nextMonth").click(nextMonth);
  loadData();
  setInterval(loadData, 20000);
});

function loadData() {
  $.get('https://licensingplus-java.herokuapp.com/getStatus', function(data) {
    statusData = data;
    loadMonth(currMonth, currYear);
  });
}

function prevMonth() {
  if(currMonth == 0) {
    currMonth = 11;
    currYear -= 1;
  } else {
    currMonth -= 1;
  }
  loadMonth(currMonth, currYear);
}

function nextMonth() {
  if(currMonth == 11) {
    currMonth = 0;
    currYear += 1;
  } else {
    currMonth += 1;
  }
  loadMonth(currMonth, currYear);
}

function syncDate(startDate, endDate) {
  var url = 'https://licensingplus-java.herokuapp.com/addNiprSyncDateRange?startDate=' + startDate + '&endDate=' + endDate;
  $.post(url, null, function(data) {
    if (startDate == endDate) {
      alert('Sync command sent for date: ' + startDate + '. Results will appear shortly.');
    } else {
      alert('Sync command sent for date range: ' + startDate + ' - ' + endDate + '. Results will appear shortly.');
    }
  });
}

function showInfo() {
  if(event.target.tagName == 'A') {
    // clicked sync button
    return;
  }
  console.log(event.target);
  console.log();
  var date = $(event.target).closest('td').attr('id')
  $('#infoTitle').text(date);
  $('#failedLicenses').empty();
  $(calendar.statusData[date].failedLicenses).each(function(index, item) {
    $('#failedLicenses').append('<li>' + item.licenseNumber + '</li>');
  });
}


function loadMonth(month, year) {
  calendar = new Calendar(currMonth, currYear, statusData);
  calendar.generateHTML();
  $('#title').text(calendar.monthName + ', ' + currYear);
  $('#calendar').html(calendar.getHTML());
  $('.has-data').click(showInfo);
}

// these are labels for the days of the week
cal_days_labels = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

// these are human-readable month name labels, in order
cal_months_labels = ['January', 'February', 'March', 'April',
                     'May', 'June', 'July', 'August', 'September',
                     'October', 'November', 'December'];

// these are the days of the week for each month, in order
cal_days_in_month = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

// this is the current date
cal_current_date = new Date();

function Calendar(month, year, statusData) {
  this.month = (isNaN(month) || month == null) ? cal_current_date.getMonth() : month;
  this.year  = (isNaN(year) || year == null) ? cal_current_date.getFullYear() : year;
  this.html = '';
  this.monthName = '';
  this.statusData = statusData;
}

Calendar.prototype.generateHTML = function(){

  // get first day of month
  var firstDay = new Date(this.year, this.month, 1);
  var startingDay = firstDay.getDay();

  // find number of days in month
  var monthLength = cal_days_in_month[this.month];

  // compensate for leap year
  if (this.month == 1) { // February only!
    if((this.year % 4 == 0 && this.year % 100 != 0) || this.year % 400 == 0){
      monthLength = 29;
    }
  }

  // do the header
  this.monthName = cal_months_labels[this.month]
  var html = '<table class="calendar-table">';
  html += '<tr class="calendar-header">';
  for(var i = 0; i <= 6; i++ ){
    html += '<th class="calendar-header-day">';
    html += cal_days_labels[i];
    html += '</th>';
  }
  html += '</tr><tr>';

  // fill in the days
  var day = 1;
  // this loop is for is weeks (rows)
  for (var i = 0; i < 9; i++) {
    // this loop is for weekdays (cells)
    for (var j = 0; j <= 6; j++) {
      var key = this.year + '-' + (parseInt(this.month, 10) + 1) + '-' + day;
      console.log(key);
      var data = this.statusData[key];
      var status = true;
      var errorCount = 0;
      var hasData = (data!==undefined);
      if (hasData) {
        status = data.status;
        errorCount = data.failedLicenses.length;
      }
      console.log(data);
      var cssClass = 'calendar-day ';
      cssClass += (!hasData?'no-data ':'has-data ');
      if(!status) {
        cssClass += 'has-errors';
      }
      html += '<td class="'+ cssClass + '" id="' + key + '" data-errors="' + errorCount + '">';
      if (day <= monthLength && (i > 0 || j >= startingDay)) {

        html += '<div class="date-label">'+day+'</div>';
        html += '<div class="error-count">';
        if(errorCount > 0) {
          html += errorCount + ' ERRORS';
        } else {
          html += '&nbsp;';
        }
        html += '</div>';
        if(!status) {
          html += '<a href="#" onclick="syncDate(\'' + key + '\', \'' + key + '\');">Sync</a>';
        }
        day++;
      }
      html += '</td>';
    }
    // stop making rows if we've run out of days
    if (day > monthLength) {
      break;
    } else {
      html += '</tr><tr>';
    }
  }
  html += '</tr></table>';

  this.html = html;
}


function syncRange() {
  var fromDate = $('#fromDate').val();
  var toDate = $('#toDate').val();
  if (fromDate == '') {
    alert('Please choose a date range to sync.');
    return;
  }
  if (toDate == '') {
    toDate = fromDate;
  }
  if (parseDate(fromDate) > parseDate(toDate)) {
    alert('Invalid date range.')
    return;
  }
  syncDate(fromDate, toDate);
}

function parseDate(s) {
  var b = s.split(/\D/);
  return new Date(b[0], --b[1], b[2]);
}


Calendar.prototype.getHTML = function() {
  return this.html;
}

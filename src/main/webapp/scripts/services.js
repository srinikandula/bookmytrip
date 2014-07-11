angular.module('CIS.services', [])
  .factory('quoteService', function($http) {

    var quoteRS = {};

    quoteRS.getQuotes = function() {
      return $http({
        method: 'JSONP', 
        url: 'rs/quotes'
      });
    }

    quoteRS.getQuoteDetails = function(id) {
      return $http({
        method: 'JSONP', 
        url: 'rs/quotes/'+ id 
      });
    }

    quoteRS.getQuoteHistory = function(id) {
      return $http({
        method: 'JSONP', 
        url: 'rs/quotes/'+ id +'/history'
      });
    }

    return quoteRS;
  });
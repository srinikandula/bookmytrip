angular.module('CIS', [
  'CIS.services',
  'CIS.controllers',
  'ngRoute'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.
	when("/quotes", {templateUrl: "quotes/quotesGrid.html", controller: "quoteController"}).
	when("/quotes/:id", {templateUrl: "quotes/quote.html", controller: "quoteController"}).
	otherwise({redirectTo: '/quotes'});
}]);


angular.module('CIS.controllers', []).

/* Quotes controller */
controller('quoteController', function($scope, quoteRS) {
  $scope.nameFilter = null;
  $scope.quotes= [];
  $scope.searchFilter = function (quote) {
      var re = new RegExp($scope.nameFilter, 'i');
      return !$scope.nameFilter || re.test(quote.name) || re.test(quote.description);
  };

  quoteRS.getQuotes().success(function (response) {
      //Digging into the response to get the relevant data
      $scope.quotes = response.quotes;
  });
}).

/* Quote controller */
controller('quoteController', function($scope, $routeParams, quoteRS) {
  $scope.id = $routeParams.id;
  $scope.historyList = [];
  $scope.quote = null;

  quoteRS.getQuoteDetails($scope.id).success(function (response) {
      $scope.quote = response.quotes[0]; 
  });

  quoteRS.getQuoteHistory($scope.id).success(function (response) {
      $scope.historyList = response.history; 
  }); 
});
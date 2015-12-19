appModule.controller('loginController', ['$scope', "$rootScope", "$window", "constEvent", "loginHttpService", function ($scope, $rootScope, $window, constEvent, loginHttpService) {
  $scope.showLoginForm = true;
  $scope.account = "";
  $scope.password = "";
  $scope.rememberMe = false;
  $scope.errorMsg = "";
  $scope.accountFocus = true;

  var assignEventHandlers = function() {
    $scope.$on(constEvent.EVENT_TO_LOGIN, function(event, data) {
      $scope.showLoginForm = true;
      $scope.accountFocus = true;
    });
  };

  $scope.initialize = function() {
    assignEventHandlers();
  };

  $scope.onGoToRegister = function() {
    $scope.showLoginForm = false;
    $scope.accountFocus = false;
    $rootScope.$broadcast(constEvent.EVENT_TO_REGISTER, "");
  };

  $scope.login = function(){
    loginHttpService.login($scope.account, $scope.password, $scope.rememberMe).then(
      function() {
        $scope.errorMsg = "";
        $window.location.href = "./";
      },function(message) {
        $scope.errorMsg = message;
      }
    );
  };
}]);


appModule.controller('registerController', ['$scope', "$rootScope", "constEvent", "loginHttpService", function ($scope, $rootScope, constEvent, loginHttpService) {
  $scope.showRegisterForm = false;
  $scope.account = "";
  $scope.password = "";
  $scope.errorMsg = "";
  $scope.accountFocus = false;

  var assignEventHandlers = function() {
    $scope.$on(constEvent.EVENT_TO_REGISTER, function(event, data) {
      $scope.showRegisterForm = true;
      $scope.accountFocus = true;
    });
  };

  $scope.initialize = function () {
    assignEventHandlers();
  };

  $scope.onGoToLogin = function () {
    $scope.showRegisterForm = false;
    $scope.accountFocus = false;
    $rootScope.$broadcast(constEvent.EVENT_TO_LOGIN, "");
  };

  $scope.register = function(){
    loginHttpService.register($scope.account, $scope.password).then(
      function() {
        $scope.errorMsg = "アカウントを登録しました";
      },function(message) {
        $scope.errorMsg = message;
      }
    );
  };
}]);


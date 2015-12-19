appModule.controller('editChangePasswordWindowController',['$scope', '$timeout', '$http', '$modalInstance', 'config', 'loginHttpService', function ($scope, $timeout, $http, $modalInstance, config, loginHttpService) {
  $scope.inputVal = config.inputVal;
  $scope.inputLabel = config.inputLabel;
  $scope.alertMsgModel = "";

  $timeout(function() {
    $scope.inputFocus = true;
  }, 100);

  $scope.ok = function () {
    var input = $scope.inputVal;
    if(input == ""){
      $scope.alertMsgModel = "必須入力です";
      return;
    }else if (input.length > 100){
      $scope.alertMsgModel = "長過ぎます(100桁まで)";
      return;
    }

    loginHttpService.changePassword(input).then(
      function() {
        $scope.alertMsgModel = "";
        $modalInstance.close();
      },
      function(message) {
        $scope.alertMsgModel = message;
      }
    )
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
}]);

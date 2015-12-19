appModule.controller('editTitleController',['$scope', '$timeout', '$modalInstance', 'config', function ($scope, $timeout, $modalInstance, config) {
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
    }else if (input.length > 30){
      $scope.alertMsgModel = "長過ぎます(30桁まで)";
      return;
    }
    $scope.alertMsgModel = "";
    $modalInstance.close($scope.inputVal);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
}]);

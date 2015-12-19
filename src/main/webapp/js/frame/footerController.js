appModule.controller('footerController', ['$scope', 'noteOperatorService', function ($scope, noteOperatorService) {
  $scope.getSavingStatus = function () {
    return noteOperatorService.getSavingStatus();
  };
}]);


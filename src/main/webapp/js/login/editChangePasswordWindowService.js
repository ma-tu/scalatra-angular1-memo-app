appModule.factory('editChangePasswordWindowService', ['$q', '$modal', function($q, $modal){

  return {
    showChangePassword : function() {
      var deferred = $q.defer();
      var modalInstance = $modal.open({
        templateUrl: "template/edit_window.html",
        controller: 'editChangePasswordWindowController',
        resolve: {
          config: function () {
            return {inputLabel:"Password", inputVal:""}
          }
        }
      });
      modalInstance.result.then(function (newPassword) {
        //by close
        deferred.resolve(newPassword);
      }, function () {
        //by dismiss
        deferred.reject();
      });
      return deferred.promise;
    }
  }
}]);

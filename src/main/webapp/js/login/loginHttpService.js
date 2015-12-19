appModule.factory('loginHttpService', ['$http', '$q', function($http, $q) {

  var httpPost = function(postRequest){
    var deferred = $q.defer();

    $http({
      method: 'POST',
      url: postRequest.url,
      data: postRequest.data
    }).success(function (data) {
      //成功
      deferred.resolve(data);
    }).error(function () {
      //失敗
      deferred.reject("通信エラーが発生しました");
    });
    return deferred.promise;
  };

  return {
    login : function(account, password, rememberMe) {
      var param = {
          account: account,
          password: password,
          rememberMe: rememberMe
        };

      var deferred = $q.defer();
      httpPost(
        {
          url: './api/login/login',
          data: param
        }
      ).then(
        function(data) {
          var result = angular.fromJson(data);
          if (result.result){
            deferred.resolve();
          }else{
            deferred.reject(result.message);
          }
        },
        function(message) {
          deferred.reject(message);
        }
      );
      return deferred.promise;
    },

    register : function(account, password) {
      var param = {
        account: account,
        password: password
      };

      var deferred = $q.defer();
      httpPost(
        {
          url: './api/login/register',
          data: param
        }
      ).then(
        function(data) {
          var result = angular.fromJson(data);
          if (result.result){
            deferred.resolve();
          }else{
            deferred.reject(result.message);
          }
        },
        function(message) {
          deferred.reject(message);
        }
      );
      return deferred.promise;
    },

    changePassword : function(password) {
      var param = {
        password: password
      };

      var deferred = $q.defer();
      httpPost(
        {
          url: './api/login/changepassword',
          data: param
        }
      ).then(
        function(data) {
          var result = angular.fromJson(data);
          if (result.result){
            deferred.resolve();
          }else{
            deferred.reject(result.message);
          }
        },
        function(message) {
          deferred.reject(message);
        }
      );
      return deferred.promise;
    }
  }
}]);

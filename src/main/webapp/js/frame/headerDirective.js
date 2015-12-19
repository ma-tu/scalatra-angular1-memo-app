appModule.directive('headerDirective', function() {
  return {
    restrict: 'E',
    scope:{
      user: '@'
    },
    templateUrl: 'template/header_directive.html'
  };
});
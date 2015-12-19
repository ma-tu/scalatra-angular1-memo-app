appModule.directive('focusDirective', function(){
  return {
    restrict: 'A',
    scope: { focusDirective: '=' },
    link: function(scope, elem, attrs){
      scope.$watch('focusDirective', function(value){
        if (value) {
          elem.focus();
          elem.select();
        }
      });
      elem.on('blur', function(){
        scope.$apply(function(){
          scope.focusDirective = false;
        });
      });
    }
  };
});
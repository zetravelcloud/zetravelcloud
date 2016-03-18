'use strict';

angular.module('zetravelcloudApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



'use strict';

angular.module('zetravelcloudApp')
    .factory('ClientFinancials', function ($resource, DateUtils) {
        return $resource('api/clientFinancialss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

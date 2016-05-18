'use strict';

angular.module('zetravelcloudApp')
    .factory('Invoice', function ($resource, DateUtils) {
        return $resource('api/invoices/:id', {}, {
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

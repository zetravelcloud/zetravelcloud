'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('client', {
                parent: 'entity',
                url: '/clients',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.client.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/clients.html',
                        controller: 'ClientController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('client.detail', {
                parent: 'entity',
                url: '/client/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.client.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/client-detail.html',
                        controller: 'ClientDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Client', function($stateParams, Client) {
                        return Client.get({id : $stateParams.id});
                    }]
                }
            })
            .state('client.new', {
                parent: 'client',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/client/client-dialog.html',
                        controller: 'ClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    email: null,
                                    mobile: null,
                                    fax: null,
                                    image: null,
                                    imageContentType: null,
                                    clientType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('client');
                    })
                }]
            })
            .state('client.edit', {
                parent: 'client',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/client/client-dialog.html',
                        controller: 'ClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Client', function(Client) {
                                return Client.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('client.delete', {
                parent: 'client',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/client/client-delete-dialog.html',
                        controller: 'ClientDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Client', function(Client) {
                                return Client.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('traveler', {
                parent: 'entity',
                url: '/travelers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.traveler.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/traveler/travelers.html',
                        controller: 'TravelerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traveler');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('traveler.detail', {
                parent: 'entity',
                url: '/traveler/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.traveler.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/traveler/traveler-detail.html',
                        controller: 'TravelerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traveler');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Traveler', function($stateParams, Traveler) {
                        return Traveler.get({id : $stateParams.id});
                    }]
                }
            })
            .state('traveler.new', {
                parent: 'traveler',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/traveler/traveler-dialog.html',
                        controller: 'TravelerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    email: null,
                                    phone: null,
                                    dateOfBirth: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('traveler', null, { reload: true });
                    }, function() {
                        $state.go('traveler');
                    })
                }]
            })
            .state('traveler.edit', {
                parent: 'traveler',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/traveler/traveler-dialog.html',
                        controller: 'TravelerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Traveler', function(Traveler) {
                                return Traveler.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('traveler', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('traveler.delete', {
                parent: 'traveler',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/traveler/traveler-delete-dialog.html',
                        controller: 'TravelerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Traveler', function(Traveler) {
                                return Traveler.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('traveler', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

import { useEffect, useState } from "react";
import DashboardCard from "./DashboardCard";
import "./Dashboard.css";

import { listarVehiculos } from "../../services/vehiculoService";
import { listarConductores } from "../../services/conductoresService";
import { listarMantenciones } from "../../services/mantencionService";
import { listarFallas } from "../../services/fallaService";

import { listarSoap } from "../../services/soapService";
import { listarRevisiones } from "../../services/revisionTecnicaService";
import { listarPermisos } from "../../services/permisoCirculacionService";
import { listarEmisiones } from "../../services/emisionesService";

function DashboardPage() {

    const [vehiculos, setVehiculos] = useState([]);
    const [conductores, setConductores] = useState([]);
    const [mantenciones, setMantenciones] = useState([]);
    const [fallas, setFallas] = useState([]);

    const [soaps, setSoaps] = useState([]);
    const [revisiones, setRevisiones] = useState([]);
    const [permisos, setPermisos] = useState([]);
    const [emisiones, setEmisiones] = useState([]);

    useEffect(() => {
        cargarDashboard();
    }, []);


    const cargarDashboard = async () => {
        try {
            const [
                vehiculosData,
                conductoresData,
                mantencionesData,
                fallasData,
                soapsData,
                revisionesData,
                permisosData,
                emisionesData

            ] = await Promise.all([

                listarVehiculos(),
                listarConductores(),
                listarMantenciones(),
                listarFallas(),
                listarSoap(),
                listarRevisiones(),
                listarPermisos(),
                listarEmisiones()

            ]);
            console.log(vehiculosData);
            console.log(conductoresData);
            console.log(mantencionesData);
            console.log(fallasData);
            console.log(soapsData);
            console.log(revisionesData);
            console.log(permisosData);
            console.log(emisionesData);

            setVehiculos(vehiculosData.data || []);
            setConductores(conductoresData.data || []);
            setMantenciones(mantencionesData.data || []);
            setFallas(fallasData.data || []);

            setSoaps(soapsData.data || []);
            setRevisiones(revisionesData.data || []);
            setPermisos(permisosData.data || []);
            setEmisiones(emisionesData.data || []);

        } catch (error) {

            console.error(error);

        }

    };

    const hoy = new Date();

    const diasRestantes = (fecha) => {

        const vencimiento = new Date(fecha);

        return Math.ceil(
            (vencimiento - hoy) / (1000 * 60 * 60 * 24)
        );

    };

    //=============================
    // ALERTAS DOCUMENTOS
    //=============================

    const soapVencer = soaps.filter(s =>
    diasRestantes(s.fechaVencimiento) <= 30
);

    const revisionVencer = revisiones.filter(r =>
    diasRestantes(r.fechaVencimiento) <= 30
);

    const permisoVencer = permisos.filter(p =>
    diasRestantes(p.fechaVencimiento) <= 30
);

    const emisionesVencer = emisiones.filter(e =>
    diasRestantes(e.fechaVencimiento) <= 30
);

    //=============================
    // TABLA DOCUMENTOS
    //=============================

    const documentos = [

        ...soapVencer.map(s => ({
            patente: s.patente,
            documento: "SOAP",
            fecha: s.fechaVencimiento
        })),

        ...revisionVencer.map(r => ({
            patente: r.patente,
            documento: "Revisión Técnica",
            fecha: r.fechaVencimiento
        })),

        ...permisoVencer.map(p => ({
            patente: p.patente,
            documento: "Permiso",
            fecha: p.fechaVencimiento
        })),

        ...emisionesVencer.map(e => ({
            patente: e.patente,
            documento: "Emisiones",
            fecha: e.fechaVencimiento
        }))

    ].sort((a, b) => new Date(a.fecha) - new Date(b.fecha));

    //=============================
    // PRÓXIMAS MANTENCIONES
    //=============================

    const proximasMantenciones = mantenciones
    .filter(m =>
        m.kilometrajeRestantes <= 2000 &&
        m.kilometrajeRestantes >= 0
    )
    .sort((a, b) => a.kilometrajeRestantes - b.kilometrajeRestantes);

    return (

        <div className="dashboard">

            <h1>Dashboard Operacional</h1>

            {/* KPIs */}

            <div className="dashboard-grid">

                <DashboardCard
                    title="Vehículos"
                    value={vehiculos.length}
                    icon="🚛"
                    color="#1976d2"
                />

                <DashboardCard
                    title="Conductores"
                    value={conductores.length}
                    icon="👨‍✈️"
                    color="#43a047"
                />

                

                <DashboardCard
                    title="Fallas"
                    value={fallas.length}
                    icon="⚠️"
                    color="#e53935"
                />

                <DashboardCard
                    title="Conductores"
                    value={conductores.length}
                    icon="👨‍✈️"
                />

                <DashboardCard
                    title="Mantenciones"
                    value={mantenciones.length}
                    icon="🔧"
                />


            </div>

            {/* ALERTAS */}

            <div className="alertas-card">

                <h2>Documentación próxima a vencer (30 días)</h2>

                <div className="alertas-grid">

                    <div>🛡 SOAP <strong>{soapVencer.length}</strong></div>

                    <div>📋 Revisión Técnica <strong>{revisionVencer.length}</strong></div>

                    <div>📄 Permisos <strong>{permisoVencer.length}</strong></div>

                    <div>🌱 Emisiones <strong>{emisionesVencer.length}</strong></div>

                </div>

            </div>

            {/* TABLA DOCUMENTOS */}

            <div className="tabla-card">

                <h2>Próximos vencimientos</h2>

                <table>

                    <thead>

                    <tr>

                        <th>Patente</th>
                        <th>Documento</th>
                        <th>Vence</th>
                        <th>Días</th>

                    </tr>

                    </thead>

                    <tbody>

                    {documentos.map((d, index) => (

                        <tr key={index}>

                            <td>{d.patente}</td>

                            <td>{d.documento}</td>

                            <td>{d.fecha}</td>

                            <td>{diasRestantes(d.fecha)}</td>

                        </tr>

                    ))}

                    </tbody>

                </table>

            </div>

            {/* PRÓXIMAS MANTENCIONES */}

            <div className="tabla-card">

                <h2>Próximas Mantenciones (&lt; 2.000 km)</h2>

                <table>

                    <thead>

                    <tr>

                        <th>Patente</th>
                        <th>Km Actual</th>
                        <th>Próxima</th>
                        <th>Faltan</th>

                    </tr>

                    </thead>

                    <tbody>

                        {proximasMantenciones.map((m) => (

                            <tr key={m.id}>
                                <td>{m.patente}</td>
                                <td>{m.kilometrajeActual.toLocaleString("es-CL")}</td>
                                <td>{m.kilometraje.toLocaleString("es-CL")}</td>
                                <td>{m.kilometrajeRestantes.toLocaleString("es-CL")} km</td>
                            </tr>
                        ))}

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default DashboardPage;
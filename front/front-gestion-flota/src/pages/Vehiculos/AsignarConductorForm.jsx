import "./AsignarConductorForm.css";

import { useEffect, useState } from "react";

import { listarConductores } from "../../services/conductoresService";

function AsignarConductorForm({ vehiculo, onSubmit }) {

    const [conductores, setConductores] = useState([]);

    const [conductorId, setConductorId] = useState("");

    useEffect(() => {

        cargarConductores();

    }, []);

    const cargarConductores = async () => {

    const response = await listarConductores();

    setConductores(response.data);

};

    const handleSubmit = (e) => {

        e.preventDefault();

        onSubmit(conductorId);

    };

    return (

        <form className="asignar-form" onSubmit={handleSubmit}>

    <div className="vehiculo-info">

        <label>Vehículo</label>

        <h3>{vehiculo?.patente}</h3>

    </div>

    <div className="form-group">

        <label>Conductor</label>

        <select
            value={conductorId}
            onChange={(e) => setConductorId(e.target.value)}
        >

            <option value="">
                Seleccione un conductor
            </option>

            {conductores.map(c => (

                <option key={c.id} value={c.id}>
                    {c.nombre}
                </option>

            ))}

        </select>

    </div>

    <div className="form-actions">

        <button
            type="submit"
            className="btn-asignar-conductor"
        >
            Asignar Conductor
        </button>

    </div>

</form>

    );

}

export default AsignarConductorForm;